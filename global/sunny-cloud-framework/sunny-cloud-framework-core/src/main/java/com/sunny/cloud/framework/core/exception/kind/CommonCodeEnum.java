package com.sunny.cloud.framework.core.exception.kind;

import lombok.Getter;

@Getter
public enum CommonCodeEnum {
    SUCCESS(200, "消息处理成功"),
    ARGUMENT_INVALID(400, "参数校验不合法"),
    EXCEPTION(500, "系统繁忙,请稍后重试");
    private final Integer code;
    private final String message;

    CommonCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
