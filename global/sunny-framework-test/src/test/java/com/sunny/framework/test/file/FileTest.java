package com.sunny.framework.test.file;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.file.excel.EasyExcelReader;
import com.sunny.framework.file.model.ExcelResult;
import com.sunny.framework.test.model.excel.UserImport;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testReadMap() throws FileNotFoundException {
        File userImportFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "excel/user-import.xlsx");
        EasyExcelReader<Map<String, Object>> reader = new EasyExcelReader<>(objectMapper, stringRedisTemplate, validator);
        reader.setIndexNameMap(Map.of(1, "name", 2, "age", 3, "birthDay"));
        reader.process(ctx -> {
            ctx.getRows().forEach(t -> {
                ctx.getSuccessTotal().incrementAndGet();
            });
        });
        reader.read(new FileInputStream(userImportFile)).doReadAll();
        ExcelResult result = new ExcelResult();
        result.setToken(reader.getToken());
        result.setSuccess(reader.getTotal() == reader.getSuccessTotal().get());
        result.setSuccessTotal(reader.getSuccessTotal().get());
        result.setFailureTotal(reader.getFailureTotal());
        System.out.println();
    }

    @Test
    public void testWriter() throws Exception {
        List<List<String>> heads = new ArrayList<>();
        List<String> headName = new ArrayList<>();
        headName.add("一级");
        headName.add("姓名");
        List<String> headAge = new ArrayList<>();
        headAge.add("二级");
        headAge.add("年龄");

        List<String> headBirthDay = new ArrayList<>();
        headBirthDay.add("二级");
        headBirthDay.add("生日");

        heads.add(headName);
        heads.add(headAge);
        heads.add(headBirthDay);
        EasyExcel.write(ResourceUtils.getURL("classpath:excel/").getPath() + "user-export.xlsx").head(heads).sheet().doWrite(new ArrayList<>());
    }
}
