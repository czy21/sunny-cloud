package com.sunny.product.portal.controller;

import com.sunny.framework.core.model.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "in")
public class ProductController {



    @GetMapping(path = "p1")
    public CommonResult<Map<String, Object>> p1() {
        return CommonResult.ok(Map.of("name", "p1"));
    }


}
