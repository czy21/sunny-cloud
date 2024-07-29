package com.sunny.system.core.service;

import com.sunny.framework.core.model.PagingResult;
import com.sunny.system.core.model.dto.UserDTO;
import com.sunny.system.core.model.query.UserQuery;

public interface UserService {

    PagingResult<UserDTO> paging(UserQuery query);
}
