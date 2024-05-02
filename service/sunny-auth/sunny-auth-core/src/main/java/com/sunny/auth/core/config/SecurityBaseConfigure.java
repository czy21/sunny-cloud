package com.sunny.auth.core.config;

import com.sunny.auth.core.handler.JsonAuthenticationFailureHandler;
import com.sunny.auth.core.handler.JsonAuthenticationSuccessHandler;
import com.sunny.auth.core.model.JsonAuthenticationDetailsSource;
import com.sunny.auth.core.provider.JsonLoginAuthenticationProvider;
import com.sunny.auth.core.service.JsonLoginUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityBaseConfigure {

    @Bean
    public JsonAuthenticationDetailsSource jsonAuthenticationDetailsSource() {
        return new JsonAuthenticationDetailsSource();
    }

    @Bean
    public UserDetailsService jsonLoginUserDetailsService() {
        return new JsonLoginUserDetailsServiceImpl();
    }

    @Bean
    public JsonLoginAuthenticationProvider jsonLoginAuthenticationProvider() {
        return new JsonLoginAuthenticationProvider();
    }

    @Bean
    public JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        return new JsonAuthenticationSuccessHandler();
    }

    @Bean
    public JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return new JsonAuthenticationFailureHandler();
    }
}
