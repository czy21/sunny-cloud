package com.sunny.auth.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.core.filter.JsonLoginAuthenticationFilter;
import com.sunny.auth.core.handler.JsonAuthenticationFailureHandler;
import com.sunny.auth.core.handler.JsonAuthenticationSuccessHandler;
import com.sunny.auth.core.model.JsonAuthenticationDetailsSource;
import com.sunny.auth.core.provider.JsonLoginAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfigure {

    JdbcTemplate jdbcTemplate;
    ObjectMapper objectMapper;
    JsonLoginAuthenticationEntryPoint jsonLoginAuthenticationEntryPoint;
    JsonAuthenticationDetailsSource jsonAuthenticationDetailsSource;
    JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler;
    JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler;

    @Bean
    @Order(2)
    public SecurityFilterChain standardSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(t ->
                t.anyRequest().authenticated()
        );
        http.anonymous(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
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
