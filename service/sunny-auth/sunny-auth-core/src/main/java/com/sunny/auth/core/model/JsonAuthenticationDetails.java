package com.sunny.auth.core.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class JsonAuthenticationDetails {

    private String remoteAddress;

    public JsonAuthenticationDetails(){
    }

    public JsonAuthenticationDetails(HttpServletRequest request) {
        this(request.getRemoteAddr());
    }

    public JsonAuthenticationDetails(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    private static String extractSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? session.getId() : null;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
