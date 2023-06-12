package com.sunny.cloud.framework.file.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.cloud.framework.file.listener.ExcelGenericDataEventListener;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.InputStream;
import java.util.UUID;
import java.util.function.Consumer;

public class EasyExcelReader<T extends BaseExcelDataModel> {

    private InputStream inputStream;
    private String token;
    private int batch = 2000;
    private int expireMinutes = 30;
    private ExcelGenericDataEventListener<T> excelGenericDataEventListener;

    public EasyExcelReader<T> file(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public EasyExcelReader<T> batch(int batch) {
        if (batch > 0) {
            this.batch = batch;
        }
        return this;
    }

    /**
     * 过期时间(分钟)
     *
     * @param minutes
     * @return
     */
    public EasyExcelReader<T> expire(int minutes) {
        if (minutes > expireMinutes) {
            this.expireMinutes = minutes;
        }
        return this;
    }

    public String getToken() {
        return token;
    }

    public EasyExcelReader<T> process(Consumer<ExcelGenericDataEventListener.Context<T>> consumer,
                                      ObjectMapper objectMapper,
                                      StringRedisTemplate redisTemplate) {
        this.token = UUID.randomUUID().toString().replace("-", "");
        this.excelGenericDataEventListener = new ExcelGenericDataEventListener<>(consumer, objectMapper);
        this.excelGenericDataEventListener.setToken(token);
        this.excelGenericDataEventListener.setBatch(batch);
        this.excelGenericDataEventListener.setExpireMinutes(expireMinutes);
        this.excelGenericDataEventListener.setRedisTemplate(redisTemplate);
        return this;
    }

    public int getTotal() {
        return excelGenericDataEventListener.getTotal();
    }

    public ExcelReaderBuilder head(Class<T> clazz) {
        return EasyExcel.read(inputStream, clazz, excelGenericDataEventListener);
    }
}
