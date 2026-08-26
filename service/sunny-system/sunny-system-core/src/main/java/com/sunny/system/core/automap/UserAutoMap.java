package com.sunny.system.core.automap;

import com.sunny.framework.core.automap.BaseAutoMap;
import com.sunny.framework.core.automap.CentralConfig;
import com.sunny.system.core.model.dto.UserDTO;
import com.sunny.system.core.model.SysUser;
import org.mapstruct.Mapper;

@Mapper(config = CentralConfig.class)
public interface UserAutoMap extends BaseAutoMap<SysUser, UserDTO> {
}
