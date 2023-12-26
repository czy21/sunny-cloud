package com.sunny.finance.portal.controller;

import com.sunny.framework.core.model.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "in")
public class InController {

//    @Value("${name}")
    String name;

    @GetMapping(path = "a")
    public CommonResult<Map<String, Object>> a() {
        return CommonResult.ok(Map.of("name", ""));
    }


}
