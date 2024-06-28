package com.sunny.system.portal.controller;

import com.sunny.framework.core.model.CommonResult;
import com.sunny.system.core.service.JooqDispatcher;
import org.jooq.DSLContext;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jooq")
public class JooqController {

    @Autowired
    JooqDispatcher jooqDispatcher;
    @Autowired
    DSLContext dslContext;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping(path = "list")
    public CommonResult<List<Map<String, Object>>> list(@RequestParam String tableType, @RequestBody Map<String, Object> dto) {
        jooqDispatcher.list(tableType, dto);
        return CommonResult.ok();
    }

    @PostMapping(path = "detail")
    public CommonResult<List<Map<String, Object>>> detail(@RequestParam String tableType, @RequestBody Map<String, Object> dto) {
        jooqDispatcher.detail(tableType, dto);
        return CommonResult.ok();
    }


    @GetMapping(path = "execute")
    public CommonResult<Void> execute() {
        String sql1 = "select :first as a,:second as b,:third as c,:third as d";

        dslContext.settings().withParamType(ParamType.NAMED);
        var a=dslContext.renderNamedParams(DSL.query(sql1));
        Map<String,Object> param=new HashMap<>();
        param.put("first", 1);
        param.put("second", 2);
        param.put("third", 3);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedParameterJdbcTemplate.execute(sql1, param, (PreparedStatementCallback<Object>) PreparedStatement::executeQuery);
        return CommonResult.ok();
    }

}
