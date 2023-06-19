package com.sunny.cloud.system.core.service;

import com.sunny.cloud.framework.core.model.SimpleItemModel;
import com.sunny.cloud.system.api.model.DictDTO;
import com.sunny.cloud.system.core.model.query.SimpleQuery;

import java.util.List;
import java.util.Map;

public interface DictService {

    void add(DictDTO dto);

    void edit(DictDTO dto);

    void delete(Long id);

    Map<String, List<SimpleItemModel<Object>>> findMapByKeys(SimpleQuery query);

    DictDTO findByCode(String code);

    String getCacheKey(String code);

    void delCache(String code);
}
