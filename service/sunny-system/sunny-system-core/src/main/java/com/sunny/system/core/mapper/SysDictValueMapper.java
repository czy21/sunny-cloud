package com.sunny.system.core.mapper;

import com.sunny.system.core.model.SysDictValue;
import jakarta.annotation.Generated;
import com.sunny.system.core.model.dto.DictValueDTO;
import com.sunny.system.core.model.query.DictValueQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import java.util.List;

public interface SysDictValueMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict_value")
    int deleteByPrimaryKey(Long id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict_value")
    int insert(SysDictValue row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict_value")
    int insertSelective(SysDictValue row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict_value")
    SysDictValue selectByPrimaryKey(Long id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict_value")
    int updateByPrimaryKeySelective(SysDictValue row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict_value")
    int updateByPrimaryKey(SysDictValue row);
    
    boolean exists(@Param("po") SysDictValue po, @Param("includeId") boolean includeId);
    
    List<DictValueDTO> selectListBy(DictValueQuery query);
    
    @Update("update sys_dict_value set deleted = 1 where id = #{id}}")
    void delete(Long id);
}