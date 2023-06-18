package com.sunny.cloud.system.core.service.impl;

import com.sunny.cloud.framework.core.model.SimpleItemModel;
import com.sunny.cloud.system.api.model.DictDTO;
import com.sunny.cloud.system.core.model.query.SimpleQuery;
import com.sunny.cloud.system.core.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Map<String, List<SimpleItemModel<Object>>> findMapByKeys(SimpleQuery query) {
        return Optional.ofNullable(query.getKeys())
                .orElse(new ArrayList<>()).stream().collect(HashMap::new, (m, n) -> {

                }, Map::putAll);
    }

    @Override
    public void add(DictDTO dto) {

    }

    @Override
    public void edit(DictDTO dto) {

    }

    @Override
    public void delete(Long id) {

    }
}
