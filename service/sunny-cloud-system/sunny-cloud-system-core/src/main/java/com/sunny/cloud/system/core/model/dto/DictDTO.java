package com.sunny.cloud.system.core.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DictDTO {
    private Long id;
    private String code;
    private String name;
    private String remark;
    private Integer valueType;
    private String valueTypeName;
    List<DictValueDTO> values;
}
