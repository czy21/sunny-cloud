package com.sunny.cloud.system.api;

import com.sunny.cloud.framework.core.model.SimpleItemModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface SysDictApi {

    @RequestMapping(path = "/provider/sys/dict/getSimplesByKeys")
    List<SimpleItemModel<Object>> getSimplesByKeys(@RequestParam("keys") List<String> keys);

    @RequestMapping(path = "/provider/sys/dict/getSimpleMapByKeys")
    Map<String, List<SimpleItemModel<Object>>> getSimpleMapByKeys(@RequestParam("keys") List<String> keys);
}
