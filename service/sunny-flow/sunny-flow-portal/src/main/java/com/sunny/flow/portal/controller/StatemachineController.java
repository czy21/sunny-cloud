package com.sunny.flow.portal.controller;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.web.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "statemachine")
public class StatemachineController extends BaseController {

    @GetMapping(path = "t1")
    public CommonResult<Map<String, Object>> t1() {
        return CommonResult.ok();
    }
}
