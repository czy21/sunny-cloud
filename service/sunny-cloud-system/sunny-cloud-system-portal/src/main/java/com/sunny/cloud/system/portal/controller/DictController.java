package com.sunny.cloud.system.portal.controller;

import com.sunny.cloud.framework.core.model.CommonResult;
import com.sunny.cloud.framework.web.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "字典API")
@RestController
@RequestMapping(path = "dict")
public class DictController extends BaseController {

    @Operation(summary = "分页")
    @PostMapping(path = "page")
    public CommonResult<Map<String, Object>> page() {
        return CommonResult.ok();
    }

    @Operation(summary = "详情")
    @GetMapping(path = "detail")
    public CommonResult<Map<String, Object>> detail() {
        return CommonResult.ok();
    }

    @Operation(summary = "添加")
    @PostMapping(path = "add")
    public CommonResult<Map<String, Object>> add() {
        return CommonResult.ok();
    }

    @Operation(summary = "编辑")
    @PostMapping(path = "edit")
    public CommonResult<Map<String, Object>> edit() {
        return CommonResult.ok();
    }

    @Operation(summary = "删除")
    @PostMapping(path = "del")
    public CommonResult<Map<String, Object>> del() {
        return CommonResult.ok();
    }
}
