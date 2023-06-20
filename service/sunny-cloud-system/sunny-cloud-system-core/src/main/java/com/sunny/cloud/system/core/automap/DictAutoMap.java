package com.sunny.cloud.system.core.automap;

import com.sunny.cloud.framework.core.automap.BaseAutoMap;
import com.sunny.cloud.framework.core.automap.CentralConfig;
import com.sunny.cloud.system.core.model.dto.DictDTO;
import com.sunny.cloud.system.core.model.po.DictPO;
import com.sunny.cloud.system.core.model.vo.DictVO;
import org.mapstruct.Mapper;

@Mapper(config = CentralConfig.class)
public interface DictAutoMap extends BaseAutoMap<DictPO, DictDTO> {
    DictPO mapToPO(DictVO vo);
}
