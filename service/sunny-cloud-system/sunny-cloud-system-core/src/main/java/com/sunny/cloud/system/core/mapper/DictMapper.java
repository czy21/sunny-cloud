package com.sunny.cloud.system.core.mapper;

import com.sunny.cloud.system.core.model.po.DictPO;

public interface DictMapper {
    int insert(DictPO record);

    int update(DictPO record);
}
