package com.sunny.system.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunny.framework.core.model.PagingResult;
import com.sunny.system.core.automap.UserAutoMap;
import com.sunny.system.core.mapper.SysUserMapper;
import com.sunny.system.core.model.dto.UserDTO;
import com.sunny.system.core.model.query.UserQuery;
import com.sunny.system.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserAutoMap userAutoMap;
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public PagingResult<UserDTO> paging(UserQuery query) {
        try (Page<UserDTO> page = PageHelper.startPage(query.getPage(), query.getPageSize())) {
            PageInfo<UserDTO> pageInfo = page.doSelectPageInfo(() -> sysUserMapper.selectListBy(query));
            return PagingResult.convert(pageInfo);
        }
    }
}
