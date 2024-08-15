package com.sunny.framework.file.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.file.listener.ExcelGenericDataEventListener;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.OutputStream;
import java.util.*;
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
                        if (!StringUtils.isEmpty(errorListStr)) {
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
            } while (CollectionUtils.isNotEmpty(list));
            writer.finish();
        }
    }

    public void doWrite(OutputStream outputStream, LinkedHashMap<String, List<String>> nameHeads) {
        List<String> message = new ArrayList<>();
        message.add("错误信息");
        nameHeads.putIfAbsent("message", message);
        try (ExcelWriter writer = EasyExcel.write(outputStream).head(new ArrayList<>(nameHeads.values())).build()) {
            var boundListOperation = redisTemplate.boundListOps(ExcelGenericDataEventListener.DATA_KEY_PREFIX_FUNC.apply(token));
            int start = 0;
            int end = batch;
            List<String> list;
            do {
                list = boundListOperation.range(start, end - 1);
                List<List<Object>> targets = Optional.ofNullable(list).orElse(List.of()).stream().map(t -> {
                    try {
                        Map<String, Object> row = objectMapper.readValue(t, new TypeReference<Map<String, Object>>() {
                        });
                        String errorListStr = (String) redisTemplate.opsForHash().get(ExcelGenericDataEventListener.ERROR_KEY_PREFIX_FUNC.apply(token), String.valueOf(row.get("rowIndex")));
                        if (!StringUtils.isEmpty(errorListStr)) {
                            List<String> errors = objectMapper.readValue(errorListStr, new TypeReference<List<String>>() {
                            });
                            row.put("message", String.join(";", errors));
                        }
                        return nameHeads.keySet().stream().map(row::get).collect(Collectors.toList());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
                writer.write(targets, EasyExcel.writerSheet().build());
                start = end;
                end = start + batch;
            } while (CollectionUtils.isNotEmpty(list));
            writer.finish();
        }
    }
}
