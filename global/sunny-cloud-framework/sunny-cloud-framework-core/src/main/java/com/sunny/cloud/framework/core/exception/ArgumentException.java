package com.sunny.cloud.framework.core.exception;


import com.sunny.cloud.framework.core.exception.kind.CommonCodeEnum;

public class ArgumentException extends CommonException {
    public ArgumentException() {
        super(CommonCodeEnum.ARGUMENT_INVALID.getCode(), CommonCodeEnum.ARGUMENT_INVALID.getMessage());
    }

    public ArgumentException(String message) {
        super(CommonCodeEnum.ARGUMENT_INVALID.getCode(), message);
    }
}
