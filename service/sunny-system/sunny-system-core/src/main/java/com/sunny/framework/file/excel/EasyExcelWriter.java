package com.sunny.framework.file.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.core.model.PageIterator;
import com.sunny.framework.file.listener.ExcelGenericDataEventListener;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EasyExcelWriter<T extends BaseExcelModel> {
    private String token;
    private int batch = 4;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public EasyExcelWriter(ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public EasyExcelWriter<T> batch(int batch) {
        this.batch = batch;
        return this;
    }

    public EasyExcelWriter<T> token(String token) {
        this.token = token;
        return this;
    }

    public void doWrite(OutputStream outputStream, Class<T> head) {
        try (ExcelWriter writer = EasyExcel.write(outputStream, head).build()) {
            var boundListOperation = redisTemplate.boundListOps(ExcelGenericDataEventListener.DATA_KEY_PREFIX_FUNC.apply(token));
            int start = 0;
            int end = batch;
            List<String> list;
            do {
                list = boundListOperation.range(start, end - 1);
                List<T> targets = Optional.ofNullable(list).orElse(List.of()).stream().map(t -> {
                    try {
                        T row = objectMapper.readValue(t, head);
                        String errorListStr = (String) redisTemplate.opsForHash().get(ExcelGenericDataEventListener.ERROR_KEY_PREFIX_FUNC.apply(token), String.valueOf(row.getRowIndex()));
                        if (StringUtils.isNotEmpty(errorListStr)) {
                            List<String> errors = objectMapper.readValue(errorListStr, new TypeReference<List<String>>() {
                            });
                            row.setMessage(String.join(";", errors));
                        }
                        return row;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
                writer.write(targets, EasyExcel.writerSheet().build());
                start = end;
                end = start + batch;
            } while (list != null && !list.isEmpty());
            writer.finish();
        }
    }
}
