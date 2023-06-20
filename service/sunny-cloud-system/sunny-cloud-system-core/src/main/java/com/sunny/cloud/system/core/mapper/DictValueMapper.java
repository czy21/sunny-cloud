package com.sunny.cloud.system.core.mapper;

import com.sunny.cloud.system.core.model.dto.DictValueDTO;
import com.sunny.cloud.system.core.model.po.DictValuePO;
import com.sunny.cloud.system.core.model.query.DictValueQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DictValueMapper {


    int insert(DictValuePO record);

    int update(DictValuePO record);

    boolean exists(@Param("po") DictValuePO po, @Param("includeId") boolean includeId);

    List<DictValueDTO> selectListBy(DictValueQuery query);

    @Update("update sys_dict_value set deleted = 1 where id = #{id}}")
    void delete(Long id);
}
