package com.sunny.framework.test.mapper;

import com.sunny.framework.test.model.po.UserPO;
import org.springframework.data.repository.CrudRepository;

public interface UserMapper extends CrudRepository<UserPO, Long> {
}
