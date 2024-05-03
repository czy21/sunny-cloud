package com.sunny.auth.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.common.SecurityCommonConfigure;
import com.sunny.auth.common.SecurityProperties;
import com.sunny.auth.common.handler.SecurityExceptionHandler;
import com.sunny.auth.common.manager.SecurityPolicyRequestAuthorizationManager;
import com.sunny.auth.common.provider.JsonLoginAuthenticationEntryPoint;
import com.sunny.auth.core.filter.JsonLoginAuthenticationFilter;
import com.sunny.auth.core.handler.JsonAuthenticationFailureHandler;
import com.sunny.auth.core.handler.JsonAuthenticationSuccessHandler;
import com.sunny.auth.core.model.JsonAuthenticationDetailsSource;
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
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@EnableMethodSecurity(prePostEnabled = false)
@Import({SecurityCommonConfigure.class, SecurityExceptionHandler.class})
public class SecurityConfigure {

    ObjectMapper objectMapper;
    JsonLoginAuthenticationEntryPoint jsonLoginAuthenticationEntryPoint;
    JsonAuthenticationDetailsSource jsonAuthenticationDetailsSource;
    JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler;
    JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler;
    SecurityPolicyRequestAuthorizationManager securityPolicyRequestAuthorizationManager;

    public SecurityConfigure(ObjectMapper objectMapper,
                             JsonLoginAuthenticationEntryPoint jsonLoginAuthenticationEntryPoint,
                             JsonAuthenticationDetailsSource jsonAuthenticationDetailsSource,
                             JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler,
                             JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler,
                             SecurityPolicyRequestAuthorizationManager securityPolicyRequestAuthorizationManager) {
        this.objectMapper = objectMapper;
        this.jsonLoginAuthenticationEntryPoint = jsonLoginAuthenticationEntryPoint;
        this.jsonAuthenticationDetailsSource = jsonAuthenticationDetailsSource;
        this.jsonAuthenticationSuccessHandler = jsonAuthenticationSuccessHandler;
        this.jsonAuthenticationFailureHandler = jsonAuthenticationFailureHandler;
        this.securityPolicyRequestAuthorizationManager = securityPolicyRequestAuthorizationManager;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain standardSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(t -> t.anyRequest().access(securityPolicyRequestAuthorizationManager));
        http.anonymous(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.requestCache(RequestCacheConfigurer::disable);
        http.exceptionHandling(t -> t.authenticationEntryPoint(jsonLoginAuthenticationEntryPoint));
        http.with(new JsonLoginConfigure<>(new JsonLoginAuthenticationFilter(objectMapper)), t -> {
            t.detailsSource(jsonAuthenticationDetailsSource);
            t.successHandler(jsonAuthenticationSuccessHandler);
            t.failureHandler(jsonAuthenticationFailureHandler);
            t.securityContextRepository(new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository()));
        });
        return http.build();
    }

}
