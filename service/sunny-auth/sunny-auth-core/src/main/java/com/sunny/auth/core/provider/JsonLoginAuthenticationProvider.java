package com.sunny.auth.core.provider;

import com.sunny.auth.core.filter.JsonLoginAuthenticationToken;
import com.sunny.auth.core.model.LoginBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;


public class JsonLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginBody loginBody = (LoginBody) authentication.getCredentials();
        //TODO 校验密码
        //TODO 加载用户
        return JsonLoginAuthenticationToken.authenticated("user", "password", null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JsonLoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
