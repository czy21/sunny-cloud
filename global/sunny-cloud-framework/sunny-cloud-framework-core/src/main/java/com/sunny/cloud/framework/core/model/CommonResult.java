package com.sunny.cloud.framework.core.model;

import com.sunny.cloud.framework.core.exception.kind.CommonCodeEnum;
import com.sunny.cloud.framework.core.util.DateUtil;

import java.time.LocalDateTime;

public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return DateUtil.toTimeStamp(LocalDateTime.now());
    }

    public static <T> CommonResult<T> ok() {
        return ok(null);
    }

    public static <T> CommonResult<T> ok(T data) {
        return new CommonResult<>(CommonCodeEnum.SUCCESS.getCode(), CommonCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> error() {
        return error(CommonCodeEnum.EXCEPTION.getMessage());
    }

    public static <T> CommonResult<T> error(String msg) {
        return error(CommonCodeEnum.EXCEPTION.getCode(), msg);
    }

    public static <T> CommonResult<T> error(Integer code, String msg) {
        return new CommonResult<>(code, msg, null);
    }

    public static <T> CommonResult<T> create(int code, String message, T data) {
        return new CommonResult<>(code, message, data);
    }
}
