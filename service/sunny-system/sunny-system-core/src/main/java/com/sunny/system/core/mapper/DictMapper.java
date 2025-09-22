package com.sunny.system.core.mapper;

import com.sunny.system.core.model.dto.DictDTO;
import com.sunny.system.core.model.po.DictPO;
import com.sunny.system.core.model.query.DictQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DictMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DictPO row);

    int insertSelective(DictPO row);

    DictPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictPO row);

    int updateByPrimaryKey(DictPO row);

    @Update("update sys_dict set deleted = 1 where id = #{id}")
    void deleteById(Long id);

    DictDTO selectOneByCode(String code);
    boolean exists(@Param("po") DictPO po, @Param("includeId") boolean includeId);
    List<DictDTO> selectList(@Param("query") DictQuery query);
    DictDTO selectById(Long id);
    @Select("select code from sys_dict where id = #{id}")
    String selectCodeById(Long id);
}
