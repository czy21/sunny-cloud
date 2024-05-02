package com.sunny.auth.common.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.common.kind.AuthenticationStatusKind;
import com.sunny.auth.common.util.SecurityUtil;
import com.sunny.framework.core.model.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;


public class JsonLoginAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JsonLoginAuthenticationEntryPoint.class);

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        if (authException instanceof AuthenticationCredentialsNotFoundException) {
            SecurityUtil.writeJsonToResponse(response, CommonResult.error(AuthenticationStatusKind.TOKEN_INVALID.getCode(), AuthenticationStatusKind.TOKEN_INVALID.getMessage()), objectMapper);
            return;
        }
        logger.error("Auth exception", authException);
        SecurityUtil.writeJsonToResponse(response, CommonResult.error(authException.getMessage()), objectMapper);
    }
}
