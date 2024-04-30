package com.sunny.auth.core.config;

import com.sunny.framework.core.model.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public CommonResult<?> handleAccessDeniedException() {
        return CommonResult.error(HttpStatus.FORBIDDEN.value(), "无访问权限");
    }

}
