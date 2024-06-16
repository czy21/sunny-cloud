package com.sunny.framework.file.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.file.listener.ExcelGenericDataEventListener;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EasyExcelWriter<T extends BaseExcelDataModel> {
    private String token;
    private OutputStream outputStream;
    private Class<?> templateClass;
    private int batch = 2000;
    private StringRedisTemplate redisTemplate;
    private ObjectMapper objectMapper;

    public EasyExcelWriter(String token, OutputStream outputStream, Class<?> templateClass, ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.token = token;
        this.outputStream = outputStream;
        this.templateClass = templateClass;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public EasyExcelWriter<T> batch(int batch) {
        this.batch = batch;
        return this;
    }

    @SuppressWarnings("unchecked")
    public void doWrite() {
        try (ExcelWriter writer = EasyExcel.write(outputStream, templateClass).build()) {
            var boundListOperation = redisTemplate.boundListOps(ExcelGenericDataEventListener.DATA_KEY_PREFIX_FUNC.apply(token));
            if (Optional.ofNullable(boundListOperation.size()).orElse(0L) == 0L) {
                writer.write(List.of(), EasyExcel.writerSheet().build());
                writer.finish();
                return;
            }
            int start = 0;
            int end;
            while (true) {
                end = start + batch;
                List<String> list = boundListOperation.range(start, end - 1);
                if (CollectionUtils.isEmpty(list)) {
                    break;
                }
                start = end;
                List<T> targets = list.stream().map(t -> {
                    try {
                        T row = (T) objectMapper.readValue(t, templateClass);
                        String errorListStr = (String) redisTemplate.opsForHash().get(ExcelGenericDataEventListener.ERROR_KEY_PREFIX_FUNC.apply(token), String.valueOf(row.getRowIndex()));
                        List<String> errors = objectMapper.readValue(errorListStr, new TypeReference<>() {
                        });
                        row.setMessage(String.join(";", errors));
                        return row;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
                writer.write(targets, EasyExcel.writerSheet().build());
            }
            writer.finish();
        }
    }
}
