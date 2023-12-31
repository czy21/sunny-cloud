package com.sunny.framework.core.model;

import com.sunny.framework.core.exception.kind.CommonCodeEnum;
import com.sunny.framework.core.util.DateUtil;

import java.time.LocalDateTime;

public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;

    public CommonResult() {
    }

    public CommonResult(Integer code, String message, T data, Long timestamp) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public CommonResult(Integer code, String message, T data) {
        this(code, message, data, DateUtil.toTimeStamp(LocalDateTime.now()));
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

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return this.timestamp;
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
