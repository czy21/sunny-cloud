package com.sunny.order.core.config;

import com.sunny.framework.statemachine.kryo.KryoStateMachineSerialisationService;
import com.sunny.order.core.kind.OrderStateMachineKind;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
public class StateMachineConfigure {

    @Bean
    public StateMachineRuntimePersister<OrderStateMachineKind.State, OrderStateMachineKind.Event, String> jpaStateMachineRuntimePersister(
            JpaStateMachineRepository stateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(new JpaRepositoryStateMachinePersist<>(stateMachineRepository,new KryoStateMachineSerialisationService<>()));
    }
}
