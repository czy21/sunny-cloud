package com.sunny.framework.file.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.file.listener.ExcelGenericDataEventListener;
import jakarta.validation.Validator;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class EasyExcelReader<T> {

    private String token;
    private int batch = 2000;
    private int expireMinutes = 30;
    private ObjectMapper objectMapper;
    private StringRedisTemplate redisTemplate;
    private Validator validator;
    private ExcelGenericDataEventListener<T> excelGenericDataEventListener;

    public EasyExcelReader(ObjectMapper objectMapper, StringRedisTemplate redisTemplate, Validator validator) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.validator = validator;
    }

    public EasyExcelReader<T> batch(int batch) {
        if (batch > 0) {
            this.batch = batch;
        }
        return this;
    }

    public EasyExcelReader<T> expire(int minutes) {
        if (minutes > expireMinutes) {
            this.expireMinutes = minutes;
        }
        return this;
    }

    public EasyExcelReader<T> process(Consumer<ExcelGenericDataEventListener.Context<T>> consumer) {
        this.token = UUID.randomUUID().toString().replace("-", "");
        this.excelGenericDataEventListener = new ExcelGenericDataEventListener<>(consumer, objectMapper, redisTemplate, validator);
        this.excelGenericDataEventListener.setToken(token);
        this.excelGenericDataEventListener.setBatch(batch);
        this.excelGenericDataEventListener.setExpireMinutes(expireMinutes);
        return this;
    }

    public String getToken() {
        return token;
    }

    public int getTotal() {
        return excelGenericDataEventListener.getTotal();
    }

    public ExcelReaderBuilder read(InputStream inputStream, Class<?> head) {
        return EasyExcel.read(inputStream,excelGenericDataEventListener).head(head);
    }

    public ExcelReaderBuilder read(InputStream inputStream, List<List<String>> head) {
        return EasyExcel.read(inputStream,excelGenericDataEventListener).head(head);
    }
}
