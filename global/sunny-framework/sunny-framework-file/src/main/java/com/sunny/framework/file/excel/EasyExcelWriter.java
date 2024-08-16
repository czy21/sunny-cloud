package com.sunny.framework.file.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.file.listener.ExcelGenericDataEventListener;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EasyExcelWriter {
    private String token;
    private int batch = 4;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public EasyExcelWriter(ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public EasyExcelWriter batch(int batch) {
        this.batch = batch;
        return this;
    }

    public EasyExcelWriter token(String token) {
        this.token = token;
        return this;
    }

    public <T extends BaseExcelModel> void doWrite(Supplier<ExcelWriterBuilder> writerBuilderSupplier, Class<T> head) {
        try (ExcelWriter writer = writerBuilderSupplier.get().head(head).build()) {
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

    public <T extends BaseExcelModel> void doWrite(OutputStream outputStream, Class<T> head) {
        doWrite(() -> EasyExcel.write(outputStream), head);
    }


    public void doWrite(Supplier<ExcelWriterBuilder> writerBuilderSupplier, Map<String, EasyExcelProperty> nameProperty) {
        int messageIndex = nameProperty.values().stream().max(Comparator.comparingInt(EasyExcelProperty::getIndex)).map(EasyExcelProperty::getIndex).orElse(0) + 1;
        List<String> message = new ArrayList<>();
        message.add("错误信息");
        nameProperty.put("message", EasyExcelProperty.of(messageIndex, message));
        Map<String, List<String>> nameHead = IntStream.rangeClosed(0, messageIndex).collect(LinkedHashMap::new,
                (m, n) -> nameProperty.entrySet().stream()
                        .filter(p -> p.getValue().getIndex().equals(n))
                        .findFirst()
                        .ifPresentOrElse(p -> m.put(p.getKey(), p.getValue().getHead()), () -> m.put("empty" + n, new ArrayList<>())),
                Map::putAll);
        try (ExcelWriter writer = writerBuilderSupplier.get().head(nameHead.values().stream().collect(Collectors.toList())).build()) {
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
                        return nameHead.keySet().stream().map(row::get).collect(Collectors.toList());
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

    public void doWrite(OutputStream outputStream, Map<String, EasyExcelProperty> nameProperty) {
        doWrite(() -> EasyExcel.write(outputStream), nameProperty);
    }
}
