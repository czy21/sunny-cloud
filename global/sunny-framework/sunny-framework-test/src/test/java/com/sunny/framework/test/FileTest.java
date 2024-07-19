package com.sunny.framework.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.file.excel.EasyExcelReader;
import com.sunny.framework.file.model.ExcelResult;
import com.sunny.framework.test.model.UserImport;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
public class FileTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    Validator validator;

    @Test
    public void testReader() throws FileNotFoundException {
        File userImportFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "excel/user-import.xlsx");
        EasyExcelReader<UserImport> reader = new EasyExcelReader<>(objectMapper, stringRedisTemplate, validator);
        reader.process(ctx -> {
            ctx.getRows().forEach(t -> {


                ctx.getSuccessTotal().incrementAndGet();
            });
        });
        reader.read(new FileInputStream(userImportFile)).head(UserImport.class).doReadAll();
        ExcelResult result = new ExcelResult();
        result.setToken(reader.getToken());
        result.setSuccess(reader.getTotal() == reader.getSuccessTotal().get());
        result.setSuccessTotal(reader.getSuccessTotal().get());
        result.setFailureTotal(reader.getFailureTotal());
        System.out.println();
    }
}
