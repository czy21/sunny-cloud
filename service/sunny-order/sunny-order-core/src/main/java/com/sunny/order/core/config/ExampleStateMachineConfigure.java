package com.sunny.order.core.config;

import com.sunny.order.core.kind.ExampleStateMachineKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
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

@EnableStateMachineFactory(name = "exampleStateMachineFactory")
public class ExampleStateMachineConfigure extends EnumStateMachineConfigurerAdapter<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> {

    @Autowired
    private StateMachineRuntimePersister<ExampleStateMachineKind.State, ExampleStateMachineKind.Event, String> exampleStateMachineRuntimePersister;

    @Bean
    public StateMachineService<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> exampleStateMachineService(
            StateMachineFactory<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> exampleStateMachineFactory,
            StateMachineRuntimePersister<ExampleStateMachineKind.State, ExampleStateMachineKind.Event, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<>(exampleStateMachineFactory, stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> config)
            throws Exception {
        config.withPersistence().runtimePersister(exampleStateMachineRuntimePersister);
        config.withMonitoring().monitor(new StateMachineMonitor<>() {
            @Override
            public void transition(StateMachine<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> stateMachine, Transition<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> transition, long duration) {

            }

            @Override
            public void action(StateMachine<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> stateMachine, Function<StateContext<ExampleStateMachineKind.State, ExampleStateMachineKind.Event>, Mono<Void>> action, long duration) {

            }
        });
    }

    @Override
    public void configure(StateMachineStateConfigurer<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> states) throws Exception {
        states
                .withStates()
                .initial(ExampleStateMachineKind.State.WAIT_PAY)
                .states(EnumSet.allOf(ExampleStateMachineKind.State.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> transitions) throws Exception {
        transitions
                .withExternal()
                .source(ExampleStateMachineKind.State.WAIT_PAY).target(ExampleStateMachineKind.State.WAIT_DELIVER).event(ExampleStateMachineKind.Event.PAY)
                .action(context -> {

                })
                .and()
                .withExternal()
                .source(ExampleStateMachineKind.State.WAIT_DELIVER).target(ExampleStateMachineKind.State.WAIT_RECEIVE).event(ExampleStateMachineKind.Event.DELIVER)
                .and()
                .withExternal()
                .source(ExampleStateMachineKind.State.WAIT_RECEIVE).target(ExampleStateMachineKind.State.FINISHED).event(ExampleStateMachineKind.Event.RECEIVE)

        ;
    }
}
