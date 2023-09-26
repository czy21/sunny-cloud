package com.sunny.system.core.mapper;

import com.sunny.system.core.model.po.FilePO;

public interface FileMapper {

    int insert(FilePO entity);
    int update(FilePO entity);

}
