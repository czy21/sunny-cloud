package com.sunny.cloud.system.core.mapper;

import com.sunny.cloud.system.api.model.DictDTO;
import com.sunny.cloud.system.core.model.po.DictPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapper {
    int insert(DictPO record);

    int update(DictPO record);

    List<DictDTO> selectListByCodes(@Param("codes") List<String> codes);

    DictDTO selectOneByCode(String code);
}
