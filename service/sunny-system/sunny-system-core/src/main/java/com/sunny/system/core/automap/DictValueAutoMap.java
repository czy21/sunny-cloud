package com.sunny.system.core.automap;

import com.sunny.framework.core.automap.BaseAutoMap;
import com.sunny.framework.core.automap.CentralConfig;
import com.sunny.system.core.model.dto.DictValueDTO;
import com.sunny.system.core.model.SysDictValue;
import com.sunny.system.core.model.vo.DictValueVO;
import org.mapstruct.Mapper;

@Mapper(config = CentralConfig.class)
public interface DictValueAutoMap extends BaseAutoMap<SysDictValue, DictValueDTO> {
    SysDictValue mapToPO(DictValueVO vo);
}
