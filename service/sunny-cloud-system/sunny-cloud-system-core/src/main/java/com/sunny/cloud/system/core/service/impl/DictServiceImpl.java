package com.sunny.cloud.system.core.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.cloud.framework.core.exception.CommonException;
import com.sunny.cloud.framework.core.model.PagingResult;
import com.sunny.cloud.framework.core.model.SimpleItemModel;
import com.sunny.cloud.system.core.model.dto.DictDTO;
import com.sunny.cloud.system.core.automap.DictAutoMap;
import com.sunny.cloud.system.core.kind.DictValueKind;
import com.sunny.cloud.system.core.mapper.DictMapper;
import com.sunny.cloud.system.core.model.po.DictPO;
import com.sunny.cloud.system.core.model.query.DictQuery;
import com.sunny.cloud.system.core.model.query.SimpleQuery;
import com.sunny.cloud.system.core.model.vo.DictVO;
import com.sunny.cloud.system.core.service.DictService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl implements DictService {

    private static final Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);
    private static final String CACHE_KEY_PREFIX = "dict";
    StringRedisTemplate redisTemplate;
    ObjectMapper objectMapper;
    DictMapper dictMapper;
    DictAutoMap dictAutoMap;

    public DictServiceImpl(StringRedisTemplate redisTemplate,
                           ObjectMapper objectMapper,
                           DictMapper dictMapper,
                           DictAutoMap dictAutoMap) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper.copy();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.dictMapper = dictMapper;
        this.dictAutoMap = dictAutoMap;
    }

    @Override
    public Map<String, List<SimpleItemModel<Object>>> findMapByKeys(SimpleQuery query) {
        DictService dictService = (DictService) AopContext.currentProxy();
        return Optional.ofNullable(query.getKeys())
                .orElse(new ArrayList<>()).stream().collect(HashMap::new, (m, n) -> {
                    DictDTO dto = dictService.findByCode(n);
                    List<SimpleItemModel<Object>> v = new ArrayList<>();
                    if (dto != null) {
                        if (DictValueKind.INT.getValue().equals(dto.getValueType())) {
                            try {
                                v = dto.getValues().stream()
                                        .map(s -> SimpleItemModel.builder()
                                                .label(s.getName())
                                                .value(Integer.parseInt(s.getValue()))
                                                .sort(s.getSort())
                                                .build()
                                        ).collect(Collectors.toList());
                            } catch (NumberFormatException e) {
                                dictService.evictByCode(dto.getCode());
                                logger.error("{}: valueType error", dto.getCode());
                            }
                        } else {
                            v = dto.getValues().stream()
                                    .map(s -> SimpleItemModel.builder()
                                            .label(s.getName())
                                            .value(s.getValue())
                                            .sort(s.getSort())
                                            .build()
                                    ).collect(Collectors.toList());
                        }
                    }
                    v = v.stream().sorted(Comparator.comparing(SimpleItemModel::getSort)).collect(Collectors.toList());
                    m.put(n, v);
                }, Map::putAll);
    }

    @Cacheable(value = CACHE_KEY_PREFIX, key = "#code", unless = "#result==null")
    @Override
    public DictDTO findByCode(String code) {
        return dictMapper.selectOneByCode(code);
    }

    @CacheEvict(value = CACHE_KEY_PREFIX, key = "#code")
    @Override
    public void evictByCode(String code) {

    }

    @Override
    public PagingResult<DictDTO> page(DictQuery query) {
        return null;
    }

    @Override
    public DictDTO detail(Long id) {
        return null;
    }

    @Override
    public void add(DictVO dto) {
        DictPO po = dictAutoMap.mapToPO(dto);
        po.setId(null);
        checkUnique(po, false);
        dictMapper.insert(po);
    }

    @CacheEvict(value = CACHE_KEY_PREFIX, key = "#dto.code")
    @Override
    public void edit(DictVO dto) {
        DictPO po = dictAutoMap.mapToPO(dto);
        checkUnique(po, true);
        dictMapper.update(po);
    }

    private void checkUnique(DictPO po, boolean includeId) {
        if (dictMapper.exists(po, includeId)) {
            throw new CommonException("编码已存在");
        }
    }

    @Override
    public void delete(Long id) {
        dictMapper.deleteById(id);
    }
}
