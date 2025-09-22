package com.sunny.system.core.mapper;

import com.sunny.system.core.model.dto.DictValueDTO;
import com.sunny.system.core.model.po.DictValuePO;
import com.sunny.system.core.model.query.DictValueQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DictValueMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DictValuePO row);

    int insertSelective(DictValuePO row);

    DictValuePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictValuePO row);

    int updateByPrimaryKey(DictValuePO row);

    boolean exists(@Param("po") DictValuePO po, @Param("includeId") boolean includeId);

    List<DictValueDTO> selectListBy(DictValueQuery query);

    @Update("update sys_dict_value set deleted = 1 where id = #{id}}")
    void delete(Long id);
}
