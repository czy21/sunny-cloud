package com.sunny.order.core.config;

import com.sunny.order.core.kind.OrderStateMachineKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.monitor.StateMachineMonitor;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.transition.Transition;
import reactor.core.publisher.Mono;

import java.util.EnumSet;
import java.util.function.Function;


@EnableStateMachineFactory(name = "orderStateMachineFactory")
public class OrderStateMachineConfigure extends EnumStateMachineConfigurerAdapter<OrderStateMachineKind.State, OrderStateMachineKind.Event> {

    @Autowired
    private StateMachineRuntimePersister<OrderStateMachineKind.State, OrderStateMachineKind.Event, String> orderStateMachineRuntimePersister;

    @Bean
    public StateMachineService<OrderStateMachineKind.State, OrderStateMachineKind.Event> orderStateMachineService(
            StateMachineFactory<OrderStateMachineKind.State, OrderStateMachineKind.Event> orderStateMachineFactory,
            StateMachineRuntimePersister<OrderStateMachineKind.State, OrderStateMachineKind.Event, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<>(orderStateMachineFactory, stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStateMachineKind.State, OrderStateMachineKind.Event> config)
            throws Exception {
        config.withPersistence().runtimePersister(orderStateMachineRuntimePersister);
        config.withMonitoring().monitor(new StateMachineMonitor<>() {
            @Override
            public void transition(StateMachine<OrderStateMachineKind.State, OrderStateMachineKind.Event> stateMachine, Transition<OrderStateMachineKind.State, OrderStateMachineKind.Event> transition, long duration) {

            }

            @Override
            public void action(StateMachine<OrderStateMachineKind.State, OrderStateMachineKind.Event> stateMachine, Function<StateContext<OrderStateMachineKind.State, OrderStateMachineKind.Event>, Mono<Void>> action, long duration) {

            }
        });
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStateMachineKind.State, OrderStateMachineKind.Event> states) throws Exception {
        states
                .withStates()
                .initial(OrderStateMachineKind.State.WAIT_PAY)
                .states(EnumSet.allOf(OrderStateMachineKind.State.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStateMachineKind.State, OrderStateMachineKind.Event> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OrderStateMachineKind.State.WAIT_PAY).target(OrderStateMachineKind.State.WAIT_DELIVER).event(OrderStateMachineKind.Event.PAY)
                .action(context -> {

                })
                .and()
                .withExternal()
                .source(OrderStateMachineKind.State.WAIT_DELIVER).target(OrderStateMachineKind.State.WAIT_RECEIVE).event(OrderStateMachineKind.Event.DELIVER)
                .and()
                .withExternal()
                .source(OrderStateMachineKind.State.WAIT_RECEIVE).target(OrderStateMachineKind.State.FINISHED).event(OrderStateMachineKind.Event.RECEIVE)

        ;
    }
}
