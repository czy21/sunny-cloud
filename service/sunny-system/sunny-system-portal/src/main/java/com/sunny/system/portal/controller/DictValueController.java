package com.sunny.system.portal.controller;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.core.model.CommonValid;
import com.sunny.framework.web.controller.BaseController;
import com.sunny.system.core.model.dto.DictValueDTO;
import com.sunny.system.core.model.query.DictValueQuery;
import com.sunny.system.core.model.vo.DictValueVO;
import com.sunny.system.core.service.DictValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "字典值API")
@RestController
@RequestMapping(path = "dict/value")
public class DictValueController extends BaseController {

    @Autowired
    DictValueService dictValueService;

    @Operation(summary = "列表")
    @PostMapping(path = "list")
    public CommonResult<List<DictValueDTO>> list(@RequestBody DictValueQuery query) {
        return CommonResult.ok(dictValueService.list(query));
    }

    @Operation(summary = "添加")
    @PostMapping(path = "add")
    public CommonResult<Void> add(@Validated(CommonValid.Add.class) @RequestBody DictValueVO vo) {
        dictValueService.add(vo);
        return CommonResult.ok();
    }

    @Operation(summary = "编辑")
    @PostMapping(path = "edit")
    public CommonResult<Void> edit(@Validated(CommonValid.Edit.class) @RequestBody DictValueVO vo) {
        dictValueService.edit(vo);
        return CommonResult.ok();
    }

    @Operation(summary = "删除")
    @PostMapping(path = "del")
    public CommonResult<Void> del(@RequestParam Long id) {
        dictValueService.delete(id);
        return CommonResult.ok();
    }
}
