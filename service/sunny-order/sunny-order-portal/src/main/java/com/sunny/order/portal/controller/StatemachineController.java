package com.sunny.order.portal.controller;

import com.sunny.order.core.kind.Order2StateMachineKind;
import com.sunny.order.core.kind.OrderStateMachineKind;
import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping(path = "statemachine")
public class StatemachineController extends BaseController {

    @Autowired
    StateMachineService<OrderStateMachineKind.State, OrderStateMachineKind.Event> machineService;
    @Autowired
    StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> order2StateMachine;

    @GetMapping(path = "t1")
    public CommonResult<Map<String, Object>> t1(@RequestParam String machineId, @RequestParam String event) {
        StateMachine<OrderStateMachineKind.State, OrderStateMachineKind.Event> machine = machineService.acquireStateMachine(machineId);
        StateMachineEventResult<OrderStateMachineKind.State, OrderStateMachineKind.Event> stateEventStateMachineEventResult = machine.sendEvent(Mono.just(MessageBuilder.withPayload(OrderStateMachineKind.Event.valueOf(event)).build())).blockLast();
        machineService.releaseStateMachine(machineId);
        return CommonResult.ok();
    }

    @GetMapping(path = "t2")
    public CommonResult<Map<String, Object>> t1(@RequestParam String event) {
        order2StateMachine.startReactively().block();
        StateMachineEventResult<Order2StateMachineKind.State, Order2StateMachineKind.Event> stateEventStateMachineEventResult =
                order2StateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Order2StateMachineKind.Event.valueOf(event)).build()))
                        .blockLast();
        order2StateMachine.stopReactively().block();
        return CommonResult.ok();
    }
}
