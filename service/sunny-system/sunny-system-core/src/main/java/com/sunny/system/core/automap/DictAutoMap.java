package com.sunny.system.core.automap;

import com.sunny.framework.core.automap.BaseAutoMap;
import com.sunny.framework.core.automap.CentralConfig;
import com.sunny.system.core.model.dto.DictDTO;
import com.sunny.system.core.model.SysDict;
import com.sunny.system.core.model.vo.DictVO;
import org.mapstruct.Mapper;

@Mapper(config = CentralConfig.class)
public interface DictAutoMap extends BaseAutoMap<SysDict, DictDTO> {
    SysDict mapToPO(DictVO vo);
}
