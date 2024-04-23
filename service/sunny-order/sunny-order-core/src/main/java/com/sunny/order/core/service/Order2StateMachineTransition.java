package com.sunny.order.core.service;

import com.sunny.order.core.kind.Order2StateMachineKind;
import com.sunny.order.core.kind.Order2StateOnTransition;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(name = "order2StateMachine")
public class Order2StateMachineTransition {

    @Order2StateOnTransition(source = Order2StateMachineKind.State.WAIT_PAY, target = Order2StateMachineKind.State.WAIT_DELIVER)
    public void pay(StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> machine, Message<Order2StateMachineKind> message) {

    }

    @Order2StateOnTransition(source = Order2StateMachineKind.State.WAIT_DELIVER, target = Order2StateMachineKind.State.WAIT_RECEIVE)
    public void deliver(StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> machine,Message<Order2StateMachineKind> message) {

    }

    @Order2StateOnTransition(source = Order2StateMachineKind.State.WAIT_RECEIVE, target = Order2StateMachineKind.State.FINISHED)
    public void receive(StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> machine,Message<Order2StateMachineKind> message) {

    }
}

