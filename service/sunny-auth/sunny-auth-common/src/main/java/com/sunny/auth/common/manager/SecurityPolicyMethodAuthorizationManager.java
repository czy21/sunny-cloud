package com.sunny.auth.common.manager;

import com.sunny.auth.common.SecurityProperties;
import com.sunny.auth.common.util.SecurityUtil;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.PreAuthorizeAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.function.Supplier;

public final class SecurityPolicyMethodAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    @Autowired
    SecurityProperties securityProperties;
    PreAuthorizeAuthorizationManager preAuthorizeAuthorizationManager = new PreAuthorizeAuthorizationManager();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation object) {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes servletRequestAttributes) {
            boolean isIgnorePath = SecurityUtil.isIgnorePath(securityProperties.getIgnorePaths(), servletRequestAttributes.getRequest().getServletPath());
            if (isIgnorePath) {
                return new AuthorizationDecision(true);
            }
        }
        return preAuthorizeAuthorizationManager.check(authentication, object);
    }

}