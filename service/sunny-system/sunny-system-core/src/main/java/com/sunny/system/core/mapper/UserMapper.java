package com.sunny.system.core.mapper;

import com.sunny.system.core.model.dto.UserDTO;
import com.sunny.system.core.model.query.UserQuery;
import com.sunny.system.core.model.po.UserPO;

import java.util.List;

/**
* @author chenzhaoyu
* @description 针对表【sys_user(用户)】的数据库操作Mapper
* @createDate 2024-07-28 13:48:46
* @Entity generator.model.po.UserPO
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserPO record);

    int insertSelective(UserPO record);

    UserPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPO record);

    int updateByPrimaryKey(UserPO record);

    List<UserDTO> selectListBy(UserQuery query);

}
