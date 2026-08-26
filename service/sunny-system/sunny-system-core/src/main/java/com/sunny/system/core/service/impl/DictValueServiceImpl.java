package com.sunny.system.core.service.impl;

import com.sunny.framework.core.exception.CommonException;
import com.sunny.system.core.model.dto.DictValueDTO;
import com.sunny.system.core.automap.DictValueAutoMap;
import com.sunny.system.core.mapper.SysDictValueMapper;
import com.sunny.system.core.model.SysDictValue;
import com.sunny.system.core.model.query.DictValueQuery;
import com.sunny.system.core.model.vo.DictValueVO;
import com.sunny.system.core.service.DictValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictValueServiceImpl implements DictValueService {

    @Autowired
    SysDictValueMapper sysDictValueMapper;
    @Autowired
    DictValueAutoMap dictValueAutoMap;

    @Override
    public List<DictValueDTO> list(DictValueQuery query) {
        return sysDictValueMapper.selectListBy(query);
    }

    @Override
    public void add(DictValueVO vo) {
        SysDictValue po = dictValueAutoMap.mapToPO(vo);
        po.setId(null);
        checkUnique(po, false);
        sysDictValueMapper.insert(po);
    }

    @Override
    public void edit(DictValueVO vo) {
        SysDictValue po = dictValueAutoMap.mapToPO(vo);
        checkUnique(po, true);
        sysDictValueMapper.updateByPrimaryKeySelective(po);
    }

    private void checkUnique(SysDictValue po, boolean includeId) {
        if (sysDictValueMapper.exists(po, includeId)) {
            throw new CommonException("名称或值已存在");
        }
    }

    @Override
    public void delete(Long id) {
        sysDictValueMapper.delete(id);
    }
}
