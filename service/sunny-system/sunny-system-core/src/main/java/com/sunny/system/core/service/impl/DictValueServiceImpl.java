package com.sunny.system.core.service.impl;

import com.sunny.framework.core.exception.CommonException;
import com.sunny.system.core.model.dto.DictValueDTO;
import com.sunny.system.core.automap.DictValueAutoMap;
import com.sunny.system.core.mapper.DictValueMapper;
import com.sunny.system.core.model.po.DictValuePO;
import com.sunny.system.core.model.query.DictValueQuery;
import com.sunny.system.core.model.vo.DictValueVO;
import com.sunny.system.core.service.DictValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictValueServiceImpl implements DictValueService {

    @Autowired
    DictValueMapper dictValueMapper;
    @Autowired
    DictValueAutoMap dictValueAutoMap;

    @Override
    public List<DictValueDTO> list(DictValueQuery query) {
        return dictValueMapper.selectListBy(query);
    }

    @Override
    public void add(DictValueVO vo) {
        DictValuePO po = dictValueAutoMap.mapToPO(vo);
        po.setId(null);
        checkUnique(po, false);
        dictValueMapper.insert(po);
    }

    @Override
    public void edit(DictValueVO vo) {
        DictValuePO po = dictValueAutoMap.mapToPO(vo);
        checkUnique(po, true);
        dictValueMapper.update(po);
    }

    private void checkUnique(DictValuePO po, boolean includeId) {
        if (dictValueMapper.exists(po, includeId)) {
            throw new CommonException("名称或值已存在");
        }
    }

    @Override
    public void delete(Long id) {
        dictValueMapper.delete(id);
    }
}
