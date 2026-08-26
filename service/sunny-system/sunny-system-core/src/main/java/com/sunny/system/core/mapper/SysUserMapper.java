package com.sunny.system.core.mapper;

import com.sunny.system.core.model.SysUser;
import jakarta.annotation.Generated;
import com.sunny.system.core.model.dto.UserDTO;
import com.sunny.system.core.model.query.UserQuery;
import java.util.List;

public interface SysUserMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_user")
    int deleteByPrimaryKey(Long id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_user")
    int insert(SysUser row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_user")
    int insertSelective(SysUser row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_user")
    SysUser selectByPrimaryKey(Long id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_user")
    int updateByPrimaryKeySelective(SysUser row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: sys_user")
    int updateByPrimaryKey(SysUser row);
    
    List<UserDTO> selectListBy(UserQuery query);
}