package com.sunny.auth.core.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.sunny.auth.core.handler.JsonAuthenticationFailureHandler;
import com.sunny.auth.core.handler.JsonAuthenticationSuccessHandler;
import com.sunny.auth.core.model.JsonAuthenticationDetailsSource;
import com.sunny.auth.core.provider.JsonLoginAuthenticationEntryPoint;
import com.sunny.auth.core.provider.JsonLoginAuthenticationProvider;
import com.sunny.auth.core.service.JsonLoginUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class SecurityBaseConfigure {

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer().configure(t -> t.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS));
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver(HttpHeaders.AUTHORIZATION);
    }

    @Bean
    public SessionRepositoryCustomizer<RedisSessionRepository> redisSessionRepositorySessionRepositoryCustomizer() {
        return t -> t.setRedisKeyNamespace("u");
    }

    @Bean
    public JsonLoginAuthenticationEntryPoint jsonAuthenticationEntryPoint() {
        return new JsonLoginAuthenticationEntryPoint();
    }

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
