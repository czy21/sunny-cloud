package com.sunny.product.api;

import com.sunny.framework.core.model.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public interface ProductApi {
    @GetMapping("/feign/product/p1")
    CommonResult<Map<String, Object>> p1();
}
