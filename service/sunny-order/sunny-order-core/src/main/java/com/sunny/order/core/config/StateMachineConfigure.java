package com.sunny.order.core.config;

import com.sunny.framework.statemachine.kryo.KryoStateMachineSerialisationService;
import com.sunny.order.core.kind.OrderStateMachineKind;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.data.redis.RedisPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.redis.RedisRepositoryStateMachinePersist;
import org.springframework.statemachine.data.redis.RedisStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
public class StateMachineConfigure {

    @Bean
    public StateMachineRuntimePersister<OrderStateMachineKind.State, OrderStateMachineKind.Event, String> jpaStateMachineRuntimePersister(
            JpaStateMachineRepository stateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(new JpaRepositoryStateMachinePersist<>(stateMachineRepository,new KryoStateMachineSerialisationService<>()));
    }
    @Bean
    @Primary
    public StateMachineRuntimePersister<OrderStateMachineKind.State, OrderStateMachineKind.Event, String> redisStateMachineRuntimePersister(
            RedisStateMachineRepository stateMachineRepository) {
        return new RedisPersistingStateMachineInterceptor<>(new RedisRepositoryStateMachinePersist<>(stateMachineRepository,new KryoStateMachineSerialisationService<>()));
    }
}
