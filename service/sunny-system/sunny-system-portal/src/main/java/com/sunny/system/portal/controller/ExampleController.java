package com.sunny.system.portal.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.sunny.framework.web.controller.BaseController;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(path = "example")
@RestController
public class ExampleController extends BaseController {

    /**
     * {
     *   "name": "haode",
     *   "email": "aaa",
     *   "properties": {
     *     "age": "10",
     *     "phone": "1515454"
     *   },
     *   "extra":"{\"attr1\":\"a1\"}"
     * }
     * @param param
     * @return
     */
    @PostMapping(path = "jsonAnyGetter")
    public Model1 jsonAnyGetter(@RequestBody Model1 param) {
        return param;
    }


    @Data
    public static class Model1 {
        private String name;

        @JsonAnyGetter
        private Map<String, Object> properties=new HashMap<>();

        @JsonAnySetter
        public void add(String key, String value) {
            properties.put(key, value);
        }

        @JsonRawValue
        private String extra;
    }
}
