package com.sunny.system.portal.controller;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.system.core.service.JooqDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jooq")
public class JooqController {

    @Autowired
    JooqDispatcher jooqDispatcher;

    @PostMapping(path = "list")
    public CommonResult<List<Map<String, Object>>> list(@RequestParam String tableType, @RequestBody Map<String, Object> dto) {
        jooqDispatcher.list(tableType, dto);
        return CommonResult.ok();
    }

    @PostMapping(path = "detail")
    public CommonResult<List<Map<String, Object>>> detail(@RequestParam String tableType, @RequestBody Map<String, Object> dto) {
        jooqDispatcher.detail(tableType, dto);
        return CommonResult.ok();
    }
}
