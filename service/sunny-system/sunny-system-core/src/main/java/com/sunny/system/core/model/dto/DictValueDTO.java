package com.sunny.system.core.model.dto;

import lombok.Data;

@Data
public class DictValueDTO {
    private String id;
    private String name;
    private String value;
    private Integer sort = 0;
    private Long dictId;
}
