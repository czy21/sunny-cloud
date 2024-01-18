package com.sunny.finance.portal.controller;

import com.sunny.finance.core.client.ProductClient;
import com.sunny.framework.core.model.CommonResult;
import com.sunny.product.api.entity.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "finance")
public class FinanceController {

    @Autowired
    ProductClient productClient;

    @GetMapping(path = "f1")
    public CommonResult<Map<String,Object>> f1() {
        return productClient.p1();
    }
}
