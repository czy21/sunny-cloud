package com.sunny.cloud.system.core.kind;

public enum DictValueKind {
    INT(0, "Int"),
    STRING(1, "String");

    private Integer value;
    private String label;

    DictValueKind(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
