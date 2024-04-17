package com.sunny.flow.core.config;

import com.sunny.flow.core.kind.MachineKind;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StatemachineConfigure extends EnumStateMachineConfigurerAdapter<MachineKind.State, MachineKind.Event> {

    @Configuration
    @EnableStateMachine
    @Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    static class Config extends EnumStateMachineConfigurerAdapter<MachineKind.State, MachineKind.Event> {

        @Override
        public void configure(StateMachineConfigurationConfigurer<MachineKind.State, MachineKind.Event> config) throws Exception {
            config.withConfiguration()
                    .autoStartup(true);
        }

        @Override
        public void configure(StateMachineStateConfigurer<MachineKind.State, MachineKind.Event> states) throws Exception {
            states.withStates()
                    .initial(MachineKind.State.S1)
                    .states(EnumSet.allOf(MachineKind.State.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<MachineKind.State, MachineKind.Event> transitions) throws Exception {
            transitions.withExternal()
                    .source(MachineKind.State.S1).target(MachineKind.State.S2).event(MachineKind.Event.E1)
                    .action(context -> {
                        System.out.println(context.getEvent());
                    })
                    .and()
                    .withExternal()
                    .source(MachineKind.State.S2).target(MachineKind.State.S3).event(MachineKind.Event.E2)
                    .action(context -> {
                        System.out.println(context.getEvent());
                    });
        }

    }
}
