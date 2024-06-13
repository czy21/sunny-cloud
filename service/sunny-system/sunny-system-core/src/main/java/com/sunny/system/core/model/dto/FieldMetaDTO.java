package com.sunny.system.core.model.dto;

import lombok.Data;

@Data
public class FieldMetaDTO {
    // example: id
    private String name;
    // example: 主键
    private String desc;
    // example: java.lang.String
    private String javaType;
    // example: base or detail
    private String schemaSuffix;
    private Boolean list;
}