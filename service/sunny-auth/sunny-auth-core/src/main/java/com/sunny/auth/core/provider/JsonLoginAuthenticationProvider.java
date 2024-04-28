package com.sunny.auth.core.provider;

import com.sunny.auth.core.filter.JsonLoginAuthenticationToken;
import com.sunny.auth.core.model.LoginBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.Collections;


public class JsonLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginBody loginBody = (LoginBody) authentication.getCredentials();
        if (StringUtils.isAnyEmpty(loginBody.getUsername(), loginBody.getPassword())) {
            throw new BadCredentialsException("用户名或密码不能为空");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginBody.getUsername());
        if (userDetails == null || !loginBody.getPassword().equals(userDetails.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        return JsonLoginAuthenticationToken.authenticated(userDetails.getUsername(), userDetails.getPassword(), Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JsonLoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
