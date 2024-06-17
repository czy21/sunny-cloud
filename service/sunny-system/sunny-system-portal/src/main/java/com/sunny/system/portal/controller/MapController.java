package com.sunny.system.portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.file.excel.EasyExcelReader;
import com.sunny.framework.file.excel.EasyExcelWriter;
import com.sunny.framework.file.model.ExcelImportResult;
import com.sunny.framework.web.controller.BaseController;
import com.sunny.system.core.model.excel.UserImport;
import jakarta.validation.Validator;
import org.apache.commons.compress.utils.Lists;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "map")
public class MapController extends BaseController {

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
    public CommonResult<ExcelImportResult> importData(@RequestPart MultipartFile file) throws IOException {
        EasyExcelReader<Map<String, Object>> reader = new EasyExcelReader<>(objectMapper, stringRedisTemplate, validator);
        reader.process(context -> {

        });
        reader.read(file.getInputStream(), Stream.of(
                Stream.of("姓名").collect(Collectors.toList()),
                Stream.of("年龄").collect(Collectors.toList()),
                Stream.of("地址", "省份").collect(Collectors.toList()),
                Stream.of("地址", "城市").collect(Collectors.toList()),
                Stream.of("地址", "地区").collect(Collectors.toList())
        ).toList()).doReadAll();
        ExcelImportResult result = new ExcelImportResult();
        result.setToken(reader.getToken());
        return CommonResult.ok(result);
    }

//    @GetMapping(path = "download/error")
//    public CompletableFuture<ResponseEntity<byte[]>> downloadError(@RequestParam String token) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        EasyExcelWriter<Map<String,Object>> writer = new EasyExcelWriter<>(objectMapper, stringRedisTemplate);
//        writer.token(token);
//        writer.doWrite(outputStream, UserImport.class);
//        String fileName = URLEncoder.encode(MessageFormat.format("{0}-{1}", "user", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), StandardCharsets.UTF_8);
//        return CompletableFuture.supplyAsync(() -> downloadExcel(outputStream.toByteArray(), fileName));
//    }
}
