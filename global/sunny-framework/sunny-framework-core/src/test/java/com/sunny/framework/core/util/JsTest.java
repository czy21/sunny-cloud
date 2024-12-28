package com.sunny.framework.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class JsTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1() {
        Map<String, Object> row = new HashMap<>(Map.of(
                "query", new HashMap<>(Map.of("year", 2024)),
                "name", "world"
        ));
        Integer val = EngineUtil.getValue(row, "obj.query.year", Integer.class);
        Assert.isTrue(val == 2024, "year should be 2024");
    }
}
