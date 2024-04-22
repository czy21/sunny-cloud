package com.sunny.order.core.service;

import com.sunny.order.core.kind.OrderStateMachineKind;
import com.sunny.order.core.kind.OrderStateOnTransition;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.WithStateMachine;

//@WithStateMachine
public class OrderStateMachineTransition {

    @OrderStateOnTransition(source = OrderStateMachineKind.State.WAIT_PAY, target = OrderStateMachineKind.State.WAIT_DELIVER)
    public void pay(StateMachine<OrderStateMachineKind.State, OrderStateMachineKind.Event> machine, Message<OrderStateMachineKind> message) {

    }

    @OrderStateOnTransition(source = OrderStateMachineKind.State.WAIT_DELIVER, target = OrderStateMachineKind.State.WAIT_RECEIVE)
    public void deliver(Message<OrderStateMachineKind> message) {

    }

    @OrderStateOnTransition(source = OrderStateMachineKind.State.WAIT_RECEIVE, target = OrderStateMachineKind.State.FINISHED)
    public void receive(Message<OrderStateMachineKind> message) {

    }
}

