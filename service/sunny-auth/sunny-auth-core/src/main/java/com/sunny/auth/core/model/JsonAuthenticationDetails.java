package com.sunny.auth.core.model;

import jakarta.servlet.http.HttpServletRequest;

public class JsonAuthenticationDetails {

    private String userType;

    public JsonAuthenticationDetails() {

    }

    public JsonAuthenticationDetails(HttpServletRequest request) {

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
