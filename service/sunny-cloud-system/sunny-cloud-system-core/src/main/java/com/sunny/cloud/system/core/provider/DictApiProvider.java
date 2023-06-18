package com.sunny.cloud.system.core.provider;

import com.sunny.cloud.framework.core.model.SimpleItemModel;
import com.sunny.cloud.system.api.SysDictApi;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DictApiProvider implements SysDictApi {
    @Override
    public List<SimpleItemModel<Object>> getSimplesByKeys(List<String> keys) {
        return null;
    }

    @Override
    public Map<String, List<SimpleItemModel<Object>>> getSimpleMapByKeys(List<String> keys) {
        return null;
    }
}
