package com.sunny.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.common.SecurityCommonConfigure;
import com.sunny.auth.common.SecurityProperties;
import com.sunny.auth.common.handler.SecurityExceptionHandler;
import com.sunny.auth.common.manager.SecurityPolicyRequestAuthorizationManager;
import com.sunny.auth.common.provider.JsonLoginAuthenticationEntryPoint;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
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


@ConditionalOnProperty(prefix = SecurityProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@EnableMethodSecurity(prePostEnabled = false)
@Import({SecurityCommonConfigure.class, SecurityExceptionHandler.class})
public class SecurityConfigure {

    ObjectMapper objectMapper;
    JsonLoginAuthenticationEntryPoint jsonLoginAuthenticationEntryPoint;
    SecurityPolicyRequestAuthorizationManager securityPolicyRequestAuthorizationManager;

    public SecurityConfigure(ObjectProvider<ObjectMapper> objectMapper,
                             JsonLoginAuthenticationEntryPoint jsonLoginAuthenticationEntryPoint,
                             SecurityPolicyRequestAuthorizationManager securityPolicyRequestAuthorizationManager) {
        this.objectMapper = objectMapper.getIfAvailable(ObjectMapper::new);
        this.jsonLoginAuthenticationEntryPoint = jsonLoginAuthenticationEntryPoint;
        this.securityPolicyRequestAuthorizationManager = securityPolicyRequestAuthorizationManager;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain standardSecurityFilterChain(HttpSecurity http) throws Exception {
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
