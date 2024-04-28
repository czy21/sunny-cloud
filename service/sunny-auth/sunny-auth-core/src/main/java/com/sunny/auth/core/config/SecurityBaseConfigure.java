package com.sunny.auth.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.core.provider.JsonLoginAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class SecurityBaseConfigure {

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(ObjectMapper objectMapper) {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
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

}
