package com.sunny.maven.core.automap;

import com.sunny.framework.core.automap.BaseAutoMap;
import com.sunny.framework.core.automap.CentralConfig;
import com.sunny.maven.core.model.dto.TestDTO;
import com.sunny.maven.core.model.po.TestPO;
import org.mapstruct.Mapper;

@Mapper(config = CentralConfig.class)
public interface TestAutoAutoMap extends BaseAutoMap<TestPO, TestDTO> {
}
