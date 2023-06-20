package com.sunny.cloud.system.api.model;

import lombok.Data;

import java.util.List;

@Data
public class DictDTO {
    private Long id;
    private String code;
    private String name;
    private String remark;
    private Integer valueType;
    List<DictValueDTO> values;
}
