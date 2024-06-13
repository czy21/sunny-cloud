package com.sunny.system.core.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TableMetaDTO {

    private String tableCode;
    private String tableName;
    private String schemaPrefix;
    private List<FieldMetaDTO> fields;


}
