package generator.mapper;

import generator.domain.UserPO;

/**
* @author chenzhaoyu
* @description 针对表【sys_user(用户)】的数据库操作Mapper
* @createDate 2024-07-28 14:20:43
* @Entity generator.domain.UserPO
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserPO record);

    int insertSelective(UserPO record);

    UserPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPO record);

    int updateByPrimaryKey(UserPO record);

}
