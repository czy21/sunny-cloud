package com.sunny.system.core.kind;

import lombok.Getter;

@Getter
public enum SchemaTypeEnum {

    BASE("基表"),
    DETAIL("明细表");
    private final String desc;

    SchemaTypeEnum(String desc) {
        this.desc = desc;
    }
}
