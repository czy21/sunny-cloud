package com.sunny.cloud.system.portal.controller;

import com.sunny.cloud.framework.core.model.CommonResult;
import com.sunny.cloud.framework.core.model.PagingResult;
import com.sunny.cloud.framework.core.model.SimpleItemModel;
import com.sunny.cloud.framework.web.controller.BaseController;
import com.sunny.cloud.system.api.model.DictDTO;
import com.sunny.cloud.system.core.model.query.SimpleQuery;
import com.sunny.cloud.system.core.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "字典API")
@RestController
@RequestMapping(path = "dict")
public class DictController extends BaseController {

    @Autowired
    DictService dictService;

    @Operation(summary = "简单查询")
    @PostMapping(path = "simple/query")
    public CommonResult<Map<String, List<SimpleItemModel<Object>>>> simpleQuery(@RequestBody SimpleQuery query) {
        return CommonResult.ok(dictService.findMapByKeys(query));
    }

    @Operation(summary = "分页")
    @PostMapping(path = "page")
    public CommonResult<PagingResult<DictDTO>> page() {
        return CommonResult.ok();
    }

    @Operation(summary = "详情")
    @GetMapping(path = "detail")
    public CommonResult<DictDTO> detail() {
        return CommonResult.ok();
    }

    @Operation(summary = "添加")
    @PostMapping(path = "add")
    public CommonResult<Void> add() {
        return CommonResult.ok();
    }

    @Operation(summary = "编辑")
    @PostMapping(path = "edit")
    public CommonResult<Void> edit() {
        return CommonResult.ok();
    }

    @Operation(summary = "删除")
    @PostMapping(path = "del")
    public CommonResult<Void> del() {
        return CommonResult.ok();
    }
}
