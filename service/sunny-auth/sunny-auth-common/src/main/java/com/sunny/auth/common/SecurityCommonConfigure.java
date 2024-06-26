package com.sunny.auth.common;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.sunny.auth.common.manager.SecurityPolicyMethodAuthorizationManager;
import com.sunny.auth.common.manager.SecurityPolicyRequestAuthorizationManager;
import com.sunny.auth.common.provider.JsonLoginAuthenticationEntryPoint;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

public class SecurityCommonConfigure {
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
    public SecurityPolicyRequestAuthorizationManager securityPolicyRequestAuthorizationManager() {
        return new SecurityPolicyRequestAuthorizationManager();
    }

    @Bean
    public SecurityPolicyMethodAuthorizationManager securityPolicyMethodAuthorizationManager() {
        return new SecurityPolicyMethodAuthorizationManager();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor preAuthorize(SecurityPolicyMethodAuthorizationManager securityPolicyMethodAuthorizationManager) {
        return AuthorizationManagerBeforeMethodInterceptor.preAuthorize(securityPolicyMethodAuthorizationManager);
    }
}
