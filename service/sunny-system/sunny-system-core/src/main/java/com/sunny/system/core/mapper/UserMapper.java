package com.sunny.system.core.mapper;

import com.sunny.system.core.model.dto.UserDTO;
import com.sunny.system.core.model.query.UserQuery;
import com.sunny.system.core.model.po.UserPO;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserPO record);

    int insertSelective(UserPO record);

    UserPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPO record);

    int updateByPrimaryKey(UserPO record);

    List<UserDTO> selectListBy(UserQuery query);

}
