package com.sunny.auth.common.manager;

import com.sunny.auth.common.SecurityProperties;
import com.sunny.auth.common.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public final class SecurityPolicyRequestAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        boolean isIgnorePath = SecurityUtil.isIgnorePath(securityProperties.getIgnorePaths(), context.getRequest().getContextPath());
        if (isIgnorePath){
            return new AuthorizationDecision(true);
        }
        Authentication auth = authentication.get();
        boolean granted = auth != null && auth.isAuthenticated();
        return new AuthorizationDecision(granted);
    }
}