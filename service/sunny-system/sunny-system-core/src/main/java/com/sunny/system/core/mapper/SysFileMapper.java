package com.sunny.system.core.mapper;

import com.sunny.system.core.model.SysFile;
import jakarta.annotation.Generated;

public interface SysFileMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_file")
    int deleteByPrimaryKey(String id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_file")
    int insert(SysFile row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_file")
    int insertSelective(SysFile row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_file")
    SysFile selectByPrimaryKey(String id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_file")
    int updateByPrimaryKeySelective(SysFile row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_file")
    int updateByPrimaryKey(SysFile row);
}