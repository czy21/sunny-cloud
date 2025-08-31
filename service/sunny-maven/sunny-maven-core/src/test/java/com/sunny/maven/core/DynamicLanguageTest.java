package com.sunny.maven.core;

import com.sunny.framework.core.util.ScriptUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class DynamicLanguageTest {


    @Test
    public void testGetValue() {
        String val = ScriptUtil.getJsValue(Map.of("name", "hao"), "obj.name", String.class);
        Assert.hasText("hao", "success");
    }
}