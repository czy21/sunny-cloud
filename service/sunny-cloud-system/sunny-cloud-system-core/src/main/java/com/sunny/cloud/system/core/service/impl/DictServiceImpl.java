package com.sunny.cloud.system.core.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.cloud.framework.core.exception.CommonException;
import com.sunny.cloud.framework.core.model.PagingResult;
import com.sunny.cloud.framework.core.model.SimpleItemModel;
import com.sunny.cloud.system.api.model.DictDTO;
import com.sunny.cloud.system.core.automap.DictAutoMap;
import com.sunny.cloud.system.core.kind.DictValueKind;
import com.sunny.cloud.system.core.mapper.DictMapper;
import com.sunny.cloud.system.core.model.po.DictPO;
import com.sunny.cloud.system.core.model.query.DictQuery;
import com.sunny.cloud.system.core.model.query.SimpleQuery;
import com.sunny.cloud.system.core.model.vo.DictVO;
import com.sunny.cloud.system.core.service.DictService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Function<String, String> CACHE_KEY_PREFIX_FUNC = t -> String.join(":", "sunny", "dict", t);
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
        return Optional.ofNullable(query.getKeys())
                .orElse(new ArrayList<>()).stream().collect(HashMap::new, (m, n) -> {
                    DictDTO dto = findByCode(n);
                    List<SimpleItemModel<Object>> v = new ArrayList<>();
                    if (dto != null) {
                        if (DictValueKind.INT.getValue().equals(dto.getValueType())) {
                            try {
                                v = dto.getValues().stream()
                                        .map(s -> {
                                            SimpleItemModel<Object> sim = SimpleItemModel.of(s.getName(), Integer.parseInt(s.getValue()));
                                            sim.setSort(s.getSort());
                                            return sim;
                                        }).collect(Collectors.toList());
                            } catch (NumberFormatException e) {
                                delCache(dto.getCode());
                                logger.error("{}: valueType error", dto.getCode());
                            }
                        } else {
                            v = dto.getValues().stream()
                                    .map(s -> {
                                        SimpleItemModel<Object> sim = SimpleItemModel.of(s.getName(), s.getValue());
                                        sim.setSort(s.getSort());
                                        return sim;
                                    }).collect(Collectors.toList());
                        }
                    }
                    v = v.stream().sorted(Comparator.comparing(SimpleItemModel::getSort)).collect(Collectors.toList());
                    m.put(n, v);
                }, Map::putAll);
    }

    @Override
    public DictDTO findByCode(String code) {
        String key = getCacheKey(code);
        String dictStr = redisTemplate.opsForValue().get(key);
        try {
            if (StringUtils.isNotEmpty(dictStr)) {
                return objectMapper.readValue(dictStr, DictDTO.class);
            }
            DictDTO de = dictMapper.selectOneByCode(code);
            if (de != null && CollectionUtils.isNotEmpty(de.getValues())) {
                redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(de), 1, TimeUnit.DAYS);
                return de;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("[Dict] code: {0}", code), e);
        }
        return null;
    }

    @Override
    public String getCacheKey(String code) {
        return CACHE_KEY_PREFIX_FUNC.apply(code);
    }

    @Override
    public void delCache(String code) {
        String key = getCacheKey(code);
        redisTemplate.delete(key);
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
