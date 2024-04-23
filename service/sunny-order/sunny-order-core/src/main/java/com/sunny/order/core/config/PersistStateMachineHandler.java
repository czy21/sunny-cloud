package com.sunny.order.core.config;

import com.sunny.order.core.kind.Order2StateMachineKind;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.GenericPersistStateMachineHandler;

public class PersistStateMachineHandler extends GenericPersistStateMachineHandler<Order2StateMachineKind.State, Order2StateMachineKind.Event> {

    public PersistStateMachineHandler(StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> stateMachine) {
        super(stateMachine);
    }

    public interface PersistStateChangeListener extends GenericPersistStateChangeListener<Order2StateMachineKind.State, Order2StateMachineKind.Event> {
    }

}