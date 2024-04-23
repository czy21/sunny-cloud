package com.sunny.order.core.config;

import com.sunny.order.core.kind.Order2StateMachineKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.monitor.StateMachineMonitor;
import org.springframework.statemachine.transition.Transition;
import reactor.core.publisher.Mono;

import java.util.EnumSet;
import java.util.function.Function;


@EnableStateMachine(name = "order2StateMachine")
public class Order2StateMachineConfigure extends EnumStateMachineConfigurerAdapter<Order2StateMachineKind.State, Order2StateMachineKind.Event> {

    @Configuration
    static class PersistHandlerConfig {

        @Autowired
        private StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> stateMachine;

        @Bean
        public Persist persist() {
            return new Persist(persistStateMachineHandler());
        }

        @Bean
        public PersistStateMachineHandler persistStateMachineHandler() {
            return new PersistStateMachineHandler(stateMachine);
        }

    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<Order2StateMachineKind.State, Order2StateMachineKind.Event> config)
            throws Exception {
        config.withMonitoring().monitor(new StateMachineMonitor<>() {
            @Override
            public void transition(StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> stateMachine, Transition<Order2StateMachineKind.State, Order2StateMachineKind.Event> transition, long duration) {

            }

            @Override
            public void action(StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> stateMachine, Function<StateContext<Order2StateMachineKind.State, Order2StateMachineKind.Event>, Mono<Void>> action, long duration) {

            }
        });
    }

    @Override
    public void configure(StateMachineStateConfigurer<Order2StateMachineKind.State, Order2StateMachineKind.Event> states) throws Exception {
        states
                .withStates()
                .initial(Order2StateMachineKind.State.WAIT_PAY)
                .states(EnumSet.allOf(Order2StateMachineKind.State.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<Order2StateMachineKind.State, Order2StateMachineKind.Event> transitions) throws Exception {
        transitions
                .withExternal()
                .source(Order2StateMachineKind.State.WAIT_PAY).target(Order2StateMachineKind.State.WAIT_DELIVER).event(Order2StateMachineKind.Event.PAY)
                .action(context -> {

                })
                .and()
                .withExternal()
                .source(Order2StateMachineKind.State.WAIT_DELIVER).target(Order2StateMachineKind.State.WAIT_RECEIVE).event(Order2StateMachineKind.Event.DELIVER)
                .and()
                .withExternal()
                .source(Order2StateMachineKind.State.WAIT_RECEIVE).target(Order2StateMachineKind.State.FINISHED).event(Order2StateMachineKind.Event.RECEIVE)

        ;
    }
}
