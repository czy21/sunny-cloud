package com.sunny.cloud.system.core.automap;

import com.sunny.cloud.framework.core.automap.BaseAutoMap;
import com.sunny.cloud.framework.core.automap.CentralConfig;
import com.sunny.cloud.system.api.model.DictDTO;
import com.sunny.cloud.system.api.model.DictValueDTO;
import com.sunny.cloud.system.core.model.po.DictPO;
import com.sunny.cloud.system.core.model.po.DictValuePO;
import com.sunny.cloud.system.core.model.vo.DictVO;
import com.sunny.cloud.system.core.model.vo.DictValueVO;
import org.mapstruct.Mapper;

@Mapper(config = CentralConfig.class)
public interface DictValueAutoMap extends BaseAutoMap<DictValuePO, DictValueDTO> {
    DictValuePO mapToPO(DictValueVO vo);
}
