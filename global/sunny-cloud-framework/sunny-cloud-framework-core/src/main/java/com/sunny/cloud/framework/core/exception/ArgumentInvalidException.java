package com.sunny.cloud.framework.core.exception;


import com.sunny.cloud.framework.core.exception.kind.CommonCodeEnum;

public class ArgumentInvalidException extends CommonException {
    public ArgumentInvalidException() {
        super(CommonCodeEnum.ARGUMENT_INVALID.getCode(), CommonCodeEnum.ARGUMENT_INVALID.getMessage());
    }

    public ArgumentInvalidException(String message) {
        super(CommonCodeEnum.ARGUMENT_INVALID.getCode(), message);
    }
}
