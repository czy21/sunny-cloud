package com.sunny.auth.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.core.kind.AuthenticationStatusKind;
import com.sunny.auth.core.util.SecurityUtil;
import com.sunny.framework.core.model.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;


public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        SecurityUtil.writeJsonToResponse(response, CommonResult.error(AuthenticationStatusKind.LOGIN_FAIL.getCode(),exception.getMessage()),objectMapper);
    }
}
