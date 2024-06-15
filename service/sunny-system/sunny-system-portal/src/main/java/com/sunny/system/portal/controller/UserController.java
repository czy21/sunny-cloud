package com.sunny.system.portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.file.excel.EasyExcelReader;
import com.sunny.framework.file.excel.EasyExcelWriter;
import com.sunny.framework.file.model.ExcelImportResult;
import com.sunny.framework.web.controller.BaseController;
import com.sunny.system.core.model.excel.UserImport;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "user")
public class UserController extends BaseController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping(path = "page")
    public CommonResult<Map<String, Object>> page() {
        return CommonResult.ok(Map.of());
    }


    @PostMapping(path = "import")
    public CommonResult<ExcelImportResult> importData(@RequestPart MultipartFile file) throws IOException {
        EasyExcelReader<UserImport> reader = new EasyExcelReader<>();
        reader.file(file.getInputStream());
        reader.process(context -> {

        }, objectMapper, stringRedisTemplate);
        reader.head(UserImport.class).doReadAll();
        ExcelImportResult result = new ExcelImportResult();
        result.setToken(reader.getToken());
        return CommonResult.ok(result);
    }

    @GetMapping(path = "download/error")
    public CompletableFuture<ResponseEntity<byte[]>> downloadError(@RequestParam String token) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new EasyExcelWriter<UserImport>(token, outputStream, UserImport.class, stringRedisTemplate, objectMapper).doWrite();
        String fileName = "user.xlsx";
        return CompletableFuture.supplyAsync(() -> downloadExcel(outputStream.toByteArray(), fileName));
    }
}
