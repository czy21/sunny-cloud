package com.sunny.system.portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.file.excel.EasyExcelReader;
import com.sunny.framework.file.excel.EasyExcelWriter;
import com.sunny.framework.file.model.ExcelResult;
import com.sunny.framework.web.controller.BaseController;
import com.sunny.system.core.model.excel.UserImport;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "user")
public class UserController extends BaseController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    Validator validator;

    @PostMapping(path = "page")
    public CommonResult<Map<String, Object>> page() {
        return CommonResult.ok(Map.of());
    }


    @PostMapping(path = "import")
    public CommonResult<ExcelResult> importData(@RequestPart MultipartFile file) throws IOException {
        EasyExcelReader<UserImport> reader = new EasyExcelReader<>(objectMapper, stringRedisTemplate, validator);
        reader.process(context -> {

        });
        reader.read(file.getInputStream()).head(UserImport.class).doReadAll();
        ExcelResult result = new ExcelResult();
        result.setToken(reader.getToken());
        return CommonResult.ok(result);
    }

    @GetMapping(path = "download/error")
    public CompletableFuture<ResponseEntity<byte[]>> downloadError(@RequestParam String token) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcelWriter<UserImport> writer = new EasyExcelWriter<>(objectMapper, stringRedisTemplate);
        writer.token(token);
        writer.doWrite(outputStream, UserImport.class);
        String fileName = URLEncoder.encode(MessageFormat.format("{0}-{1}.xlsx", "user", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), StandardCharsets.UTF_8);
        return CompletableFuture.supplyAsync(() -> downloadExcel(outputStream.toByteArray(), fileName));
    }
}
