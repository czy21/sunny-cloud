package com.sunny.product.core.provider;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.product.api.ProductApi;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProductProvider implements ProductApi {

    @Override
    public CommonResult<Map<String, Object>> p1() {
        return CommonResult.ok(Map.of("name", "p2"));
    }
}
