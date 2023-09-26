package com.sunny.system.core.service;

import com.sunny.framework.core.model.PagingResult;
import com.sunny.framework.core.model.SimpleItemModel;
import com.sunny.system.core.model.dto.DictDTO;
import com.sunny.system.core.model.query.DictQuery;
import com.sunny.system.core.model.query.SimpleQuery;
import com.sunny.system.core.model.vo.DictVO;

import java.util.List;
import java.util.Map;

public interface DictService {

    PagingResult<DictDTO> page(DictQuery query);

    DictDTO detail(Long id);

    void add(DictVO dto);

    void edit(DictVO dto);

    void delete(Long id);

    Map<String, List<SimpleItemModel<Object>>> findMapByKeys(SimpleQuery query);

    DictDTO findByCode(String code);

    void evictByCode(String code);
}
