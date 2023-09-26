package com.sunny.system.core.service;

import com.sunny.system.core.model.dto.DictValueDTO;
import com.sunny.system.core.model.query.DictValueQuery;
import com.sunny.system.core.model.vo.DictValueVO;

import java.util.List;

public interface DictValueService {

    List<DictValueDTO> list(DictValueQuery query);

    void add(DictValueVO vo);

    void edit(DictValueVO vo);

    void delete(Long id);
}
