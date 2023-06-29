package com.sunny.cloud.system.core.mapper;

import com.sunny.cloud.system.core.model.po.FilePO;

public interface FileMapper {

    int insert(FilePO entity);
    int update(FilePO entity);

}
