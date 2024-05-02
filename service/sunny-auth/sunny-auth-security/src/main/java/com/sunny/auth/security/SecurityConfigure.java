package com.sunny.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.common.SecurityCommonConfigure;
import com.sunny.auth.common.SecurityProperties;
import com.sunny.auth.common.handler.SecurityExceptionHandler;
import com.sunny.auth.common.manager.SecurityPolicyRequestAuthorizationManager;
import com.sunny.auth.common.provider.JsonLoginAuthenticationEntryPoint;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Import({SecurityCommonConfigure.class, SecurityExceptionHandler.class})
@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfigure {

    ObjectMapper objectMapper;
    JsonLoginAuthenticationEntryPoint jsonLoginAuthenticationEntryPoint;

    public SecurityConfigure(ObjectProvider<ObjectMapper> objectMapper, JsonLoginAuthenticationEntryPoint jsonLoginAuthenticationEntryPoint) {
        this.objectMapper = objectMapper.getIfAvailable(ObjectMapper::new);
        this.jsonLoginAuthenticationEntryPoint = jsonLoginAuthenticationEntryPoint;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain standardSecurityFilterChain(HttpSecurity http, SecurityPolicyRequestAuthorizationManager securityPolicyRequestAuthorizationManager) throws Exception {
        http.authorizeHttpRequests(t ->
                t.anyRequest().access(securityPolicyRequestAuthorizationManager)
        );
        http.anonymous(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.requestCache(RequestCacheConfigurer::disable);
        http.exceptionHandling(t -> t.authenticationEntryPoint(jsonLoginAuthenticationEntryPoint));
        return http.build();
    }

}
