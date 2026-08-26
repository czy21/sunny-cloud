package com.sunny.system.core.mapper;

import com.sunny.system.core.model.SysDict;
import jakarta.annotation.Generated;
import com.sunny.system.core.model.dto.DictDTO;
import com.sunny.system.core.model.query.DictQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

public interface SysDictMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict")
    int deleteByPrimaryKey(Long id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict")
    int insert(SysDict row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict")
    int insertSelective(SysDict row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict")
    SysDict selectByPrimaryKey(Long id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict")
    int updateByPrimaryKeySelective(SysDict row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_dict")
    int updateByPrimaryKey(SysDict row);
    
    @Update("update sys_dict set deleted = 1 where id = #{id}")
    void deleteById(Long id);
    
    DictDTO selectOneByCode(String code);
    
    boolean exists(@Param("po") SysDict po, @Param("includeId") boolean includeId);
    
    List<DictDTO> selectList(@Param("query") DictQuery query);
    
    DictDTO selectById(Long id);
    
    @Select("select code from sys_dict where id = #{id}")
    String selectCodeById(Long id);
}