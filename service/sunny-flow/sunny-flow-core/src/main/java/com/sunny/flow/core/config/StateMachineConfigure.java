package com.sunny.flow.core.config;

import com.sunny.flow.core.kind.MachineKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.monitor.StateMachineMonitor;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.transition.Transition;
import reactor.core.publisher.Mono;

import java.util.EnumSet;
import java.util.function.Function;

@Configuration
public class StateMachineConfigure {

    @Bean
    public StateMachineService<MachineKind.State, MachineKind.Event> stateMachineService(
            StateMachineFactory<MachineKind.State, MachineKind.Event> stateMachineFactory,
            StateMachineRuntimePersister<MachineKind.State, MachineKind.Event, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachineRuntimePersister);
    }

    @Bean
    public StateMachineRuntimePersister<MachineKind.State, MachineKind.Event, String> stateMachineRuntimePersister(
            JpaStateMachineRepository stateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(stateMachineRepository);
    }

    @Configuration
    @EnableStateMachineFactory
    public static class MachineConfig extends StateMachineConfigurerAdapter<MachineKind.State, MachineKind.Event> {
        @Autowired
        private StateMachineRuntimePersister<MachineKind.State, MachineKind.Event, String> stateMachineRuntimePersister;

        @Override
        public void configure(StateMachineConfigurationConfigurer<MachineKind.State, MachineKind.Event> config)
                throws Exception {
            config.withPersistence().runtimePersister(stateMachineRuntimePersister);
            config.withMonitoring().monitor(new StateMachineMonitor<>() {
                @Override
                public void transition(StateMachine<MachineKind.State, MachineKind.Event> stateMachine, Transition<MachineKind.State, MachineKind.Event> transition, long duration) {

                }

                @Override
                public void action(StateMachine<MachineKind.State, MachineKind.Event> stateMachine, Function<StateContext<MachineKind.State, MachineKind.Event>, Mono<Void>> action, long duration) {

                }
            });
        }

        @Override
        public void configure(StateMachineStateConfigurer<MachineKind.State, MachineKind.Event> states) throws Exception {
            states
                    .withStates()
                    .initial(MachineKind.State.S1)
                    .states(EnumSet.allOf(MachineKind.State.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<MachineKind.State, MachineKind.Event> transitions) throws Exception {
            transitions
                    .withExternal()
                    .source(MachineKind.State.S1).target(MachineKind.State.S2).event(MachineKind.Event.E1)
                    .action(context -> {
                        System.out.println();
                    })
                    .and()
                    .withExternal()
                    .source(MachineKind.State.S2).target(MachineKind.State.S3).event(MachineKind.Event.E2)
                    .action(context -> {
                        System.out.println();
                    });
        }
    }
}
