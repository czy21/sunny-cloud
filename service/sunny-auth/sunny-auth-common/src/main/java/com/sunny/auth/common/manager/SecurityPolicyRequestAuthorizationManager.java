package com.sunny.auth.common.manager;

import com.sunny.auth.common.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.function.Supplier;

public final class SecurityPolicyRequestAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    SecurityProperties securityProperties;

    PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Authentication auth = authentication.get();
        boolean isIgnorePath = isIgnorePath(context.getRequest().getContextPath());
        boolean granted = isIgnorePath || auth != null && auth.isAuthenticated();
        return new AuthorizationDecision(granted);
    }

    private boolean isIgnorePath(String path) {
        if (securityProperties.getIgnorePaths() == null || securityProperties.getIgnorePaths().isEmpty()) {
            return false;
        }
        return securityProperties.getIgnorePaths().stream().anyMatch(x -> pathMatcher.match(x, path));
    }
}