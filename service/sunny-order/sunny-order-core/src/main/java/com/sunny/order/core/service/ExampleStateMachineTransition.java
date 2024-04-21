package com.sunny.order.core.service;

import com.sunny.order.core.kind.ExampleStateMachineKind;
import com.sunny.order.core.kind.ExampleStateOnTransition;
import com.sunny.order.core.kind.OrderStateMachineKind;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.WithStateMachine;

import static org.springframework.statemachine.StateMachineSystemConstants.DEFAULT_ID_EVENT_PUBLISHER;
import static org.springframework.statemachine.StateMachineSystemConstants.DEFAULT_ID_STATEMACHINEFACTORY;


@WithStateMachine
public class ExampleStateMachineTransition {

    @ExampleStateOnTransition(source = ExampleStateMachineKind.State.WAIT_PAY, target = ExampleStateMachineKind.State.WAIT_DELIVER)
    public void pay(StateMachine<ExampleStateMachineKind.State, ExampleStateMachineKind.Event> stateMachine, Message<ExampleStateMachineKind> message) {

    }

    @ExampleStateOnTransition(source = ExampleStateMachineKind.State.WAIT_DELIVER, target = ExampleStateMachineKind.State.WAIT_RECEIVE)
    public void deliver(Message<ExampleStateMachineKind> message) {

    }

    @ExampleStateOnTransition(source = ExampleStateMachineKind.State.WAIT_RECEIVE, target = ExampleStateMachineKind.State.FINISHED)
    public void receive(Message<ExampleStateMachineKind> message) {

    }
}
