package com.sunny.order.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sunny.order.core.seriliaze.SensitiveSerializer;
import lombok.Data;

@Data
public class TestDTO {

    @JsonSerialize(using = SensitiveSerializer.class)
    private String name;
}
