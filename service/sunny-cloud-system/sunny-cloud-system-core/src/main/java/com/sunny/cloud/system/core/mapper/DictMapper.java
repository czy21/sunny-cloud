package com.sunny.cloud.system.core.mapper;

import com.sunny.cloud.system.core.model.dto.DictDTO;
import com.sunny.cloud.system.core.model.po.DictPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface DictMapper {
    int insert(DictPO record);

    int update(DictPO record);

    DictDTO selectOneByCode(String code);

    boolean exists(@Param("po") DictPO po, @Param("includeId") boolean includeId);

    @Update("update sys_dict set deleted = 1 where id = #{id}")
    void deleteById(Long id);
}
