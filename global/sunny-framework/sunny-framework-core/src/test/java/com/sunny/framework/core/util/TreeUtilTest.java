package com.sunny.framework.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.core.model.SimpleItemModel;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.net.URL;
import java.util.List;

public class TreeUtilTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1() throws Exception {
        URL url = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + "tree.json");
        List<SimpleItemModel<String>> items = objectMapper.readValue(url, new TypeReference<List<SimpleItemModel<String>>>() {
        });
        TreeUtil.assemble(items);
        List<SimpleItemModel<String>> tree = TreeUtil.getTree(items, t -> t.getParentValue() == null);
        System.out.println();
    }
}