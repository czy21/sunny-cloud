package com.sunny.auth.core.provider;

import com.sunny.auth.core.filter.JsonUsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JsonUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return JsonUsernamePasswordAuthenticationToken.authenticated("user","password",null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JsonUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
