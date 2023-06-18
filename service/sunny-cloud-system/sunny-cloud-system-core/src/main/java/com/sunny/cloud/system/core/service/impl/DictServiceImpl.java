package com.sunny.cloud.system.core.service.impl;

import com.sunny.cloud.framework.core.model.SimpleItemModel;
import com.sunny.cloud.system.api.model.DictDTO;
import com.sunny.cloud.system.core.model.query.SimpleQuery;
import com.sunny.cloud.system.core.service.DictService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl implements DictService {

    @Override
    public Map<String, List<SimpleItemModel<Object>>> findMapByKeys(SimpleQuery query) {
        return null;
    }

    @Override
    public void add(DictDTO dto) {

    }

    @Override
    public void edit(DictDTO dto) {

    }

    @Override
    public void delete(Long id) {

    }
}
