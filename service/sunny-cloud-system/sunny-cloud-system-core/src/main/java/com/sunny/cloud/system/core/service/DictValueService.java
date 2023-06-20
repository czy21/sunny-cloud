package com.sunny.cloud.system.core.service;

import com.sunny.cloud.system.api.model.DictValueDTO;
import com.sunny.cloud.system.core.model.query.DictValueQuery;
import com.sunny.cloud.system.core.model.vo.DictValueVO;

import java.util.List;

public interface DictValueService {

    List<DictValueDTO> list(DictValueQuery query);

    void add(DictValueVO vo);

    void edit(DictValueVO vo);

    void delete(Long id);
}
