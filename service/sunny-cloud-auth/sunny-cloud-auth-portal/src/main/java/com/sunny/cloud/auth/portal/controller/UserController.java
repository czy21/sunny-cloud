package com.sunny.cloud.system.portal.controller;

import com.sunny.cloud.framework.core.model.CommonResult;
import com.sunny.cloud.framework.web.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "user")
public class UserController extends BaseController {

    @PostMapping(path = "page")
    public CommonResult<Map<String, Object>> page() {
        return CommonResult.ok(Map.of());
    }
}
