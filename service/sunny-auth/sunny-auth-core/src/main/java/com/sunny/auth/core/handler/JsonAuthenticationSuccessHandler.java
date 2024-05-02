package com.sunny.auth.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.common.util.SecurityUtil;
import com.sunny.framework.core.model.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;


public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        SecurityUtil.writeJsonToResponse(response, CommonResult.ok(Map.of("token", request.getSession(false).getId())), objectMapper);
    }
}
