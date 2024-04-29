package com.sunny.auth.core.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.sunny.auth.core.model.JsonAuthenticationDetailsSource;
import com.sunny.auth.core.provider.JsonLoginAuthenticationEntryPoint;
import com.sunny.auth.core.provider.JsonLoginAuthenticationProvider;
import com.sunny.auth.core.service.JsonLoginUserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
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
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Bean
    public JsonLoginAuthenticationEntryPoint jsonAuthenticationEntryPoint() {
        return new JsonLoginAuthenticationEntryPoint();
    }

    @Bean
    public SessionRepositoryCustomizer<RedisSessionRepository> redisSessionRepositorySessionRepositoryCustomizer() {
        return t -> t.setRedisKeyNamespace("u");
    }

    @Bean
    JsonAuthenticationDetailsSource jsonAuthenticationDetailsSource() {
        return new JsonAuthenticationDetailsSource();
    }

    @Bean
    public UserDetailsService jsonUserDetailsService() {
        return new JsonLoginUserDetailServiceImpl();
    }

    @Bean
    JsonLoginAuthenticationProvider jsonUsernamePasswordAuthenticationProvider() {
        return new JsonLoginAuthenticationProvider();
    }
}
