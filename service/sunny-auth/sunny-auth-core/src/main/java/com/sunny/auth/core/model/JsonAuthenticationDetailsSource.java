package com.sunny.auth.core.model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;

public class JsonAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, JsonAuthenticationDetails> {
    @Override
    public JsonAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new JsonAuthenticationDetails(context);
    }
}
