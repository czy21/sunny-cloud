package com.sunny.product.api;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.product.api.entity.ProductDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

public interface ProductApi {
    @RequestMapping("/feign/product/p1")
    CommonResult<Map<String,Object>> p1();
}
