package com.sunny.system.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import com.sunny.framework.core.model.PagingResult;
import com.sunny.system.core.automap.DictAutoMap;
import com.sunny.system.core.mapper.DictMapper;
import com.sunny.system.core.model.dto.DictDTO;
import com.sunny.system.core.model.query.DictQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DictServiceImplTest {

    @Mock
    StringRedisTemplate redisTemplate;

    @Mock
    DictMapper dictMapper;

    @Mock
    DictAutoMap dictAutoMap;

    DictServiceImpl dictService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        dictService = new DictServiceImpl(redisTemplate, objectMapper, dictMapper, dictAutoMap);
    }

    @Test
    public void testPage() {
        DictQuery query = new DictQuery();
        List<DictDTO> list = new ArrayList<>();
        DictDTO d1 = new DictDTO();
        d1.setId(1L);
        list.add(d1);
        MockedStatic<PageMethod> pageHelper = Mockito.mockStatic(PageMethod.class);

        Page<DictDTO> page = new Page<>();
        pageHelper.when(() -> PageHelper.startPage(query.getPage(), query.getPageSize())).thenReturn(page);
        page.addAll(list);

        Mockito.when(dictMapper.selectList(query)).thenReturn(list);
        PagingResult<DictDTO> dto = dictService.page(query);

        Assertions.assertEquals(dto.getList().get(0).getId(), d1.getId());
    }
}