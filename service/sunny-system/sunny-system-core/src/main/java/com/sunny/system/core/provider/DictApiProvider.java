package com.sunny.system.core.provider;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.core.model.SimpleItemModel;
import com.sunny.system.api.SysDictApi;
import com.sunny.system.core.model.query.SimpleQuery;
import com.sunny.system.core.service.DictService;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Hidden
public class DictApiProvider implements SysDictApi {

    @Autowired
    DictService dictService;

    @Override
    public CommonResult<List<SimpleItemModel<Object>>> getSimplesByKeys(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return CommonResult.ok(List.of());
        }
        SimpleQuery query = new SimpleQuery();
        query.setKeys(keys);
        return CommonResult.ok(dictService.findMapByKeys(query).values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
    }

    @Override
    public CommonResult<Map<String, List<SimpleItemModel<Object>>>> getSimpleMapByKeys(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return CommonResult.ok(Map.of());
        }
        SimpleQuery query = new SimpleQuery();
        query.setKeys(keys);
        return CommonResult.ok(dictService.findMapByKeys(query));
    }
}
