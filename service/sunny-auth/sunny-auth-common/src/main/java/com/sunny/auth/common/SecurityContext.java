package com.sunny.auth.common;

import com.sunny.auth.common.model.JsonUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContext {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static JsonUserDetails getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (JsonUserDetails) authentication.getDetails();
    }

    public static Long getUserId() {
        JsonUserDetails user = getUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}
