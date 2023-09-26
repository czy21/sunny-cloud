package com.sunny.system.api;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.core.model.SimpleItemModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface SysDictApi {

    @RequestMapping(path = "/provider/sys/dict/getSimplesByKeys")
    CommonResult<List<SimpleItemModel<Object>>> getSimplesByKeys(@RequestParam("keys") List<String> keys);

    @RequestMapping(path = "/provider/sys/dict/getSimpleMapByKeys")
    CommonResult<Map<String, List<SimpleItemModel<Object>>>> getSimpleMapByKeys(@RequestParam("keys") List<String> keys);
}
