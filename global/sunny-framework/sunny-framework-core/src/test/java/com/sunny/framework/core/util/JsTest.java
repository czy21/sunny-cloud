package com.sunny.framework.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.IntegerRange;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class JsTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1() {
        Map<String, Object> row = new HashMap<>();
        row.put("query", Map.of("year", 2024));
        row.put("name", "john");
        IntStream.rangeClosed(1, 10).forEach(i -> row.put("m" + i, 1));
        Integer yearValue = ScriptUtil.getJsValue(row, "obj.query?.year", Integer.class);
        Integer yearTotal = ScriptUtil.getJsValue(row, "Array.from({length:12}).map((t,i,a)=>Number(obj[`m${i+1}`]) || 0).reduce((a, b) => a + b)", Integer.class);
        Assert.isTrue(yearValue == 2024, "year should be 2024");
        Assert.isTrue(yearTotal == 10, "yearTotal should be 10");
    }
}
