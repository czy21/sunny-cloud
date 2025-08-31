package com.sunny.maven.portal.controller;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.maven.core.automap.TestAutoAutoMap;
import com.sunny.maven.core.model.dto.TestDTO;
import com.sunny.maven.core.model.po.TestPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "test")
public class TestController {

    @Autowired
    TestAutoAutoMap testAutoAutoMap;


    @PostMapping("testAutoMap")
    public CommonResult<Map<String,Object>> testAutoMap(@RequestBody TestDTO param){
        TestPO po= testAutoAutoMap.mapToSource(param);
        return CommonResult.ok(Map.of());
    }

}
