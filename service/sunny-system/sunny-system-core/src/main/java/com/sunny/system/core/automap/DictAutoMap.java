package com.sunny.system.core.automap;

import com.sunny.framework.core.automap.BaseAutoMap;
import com.sunny.framework.core.automap.CentralConfig;
import com.sunny.system.core.model.dto.DictDTO;
import com.sunny.system.core.model.po.DictPO;
import com.sunny.system.core.model.vo.DictVO;
import org.mapstruct.Mapper;

@Mapper(config = CentralConfig.class)
public interface DictAutoMap extends BaseAutoMap<DictPO, DictDTO> {
    DictPO mapToPO(DictVO vo);
}
