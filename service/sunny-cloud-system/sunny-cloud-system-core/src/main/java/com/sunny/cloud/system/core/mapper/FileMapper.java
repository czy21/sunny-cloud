package com.sunny.cloud.system.core.mapper;

import com.sunny.cloud.system.core.model.po.FilePO;

public interface FileMapper {

    int insert(FilePO record);
    int update(FilePO record);

}
