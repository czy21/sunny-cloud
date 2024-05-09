package com.sunny.auth.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AuthProperties.PREFIX)
public class AuthProperties {
    public static final String PREFIX = "sunny.auth";
    private String loginUri;

    public String getLoginUri() {
        return loginUri;
    }

    public void setLoginUri(String loginUri) {
        this.loginUri = loginUri;
    }
}
