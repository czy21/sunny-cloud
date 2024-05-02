package com.sunny.order.portal.controller;

import com.sunny.order.core.config.Persist;
import com.sunny.order.core.kind.Order2StateMachineKind;
import com.sunny.order.core.kind.OrderStateMachineKind;
import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    Persist persist;

    @GetMapping(path = "t1")
    public CommonResult<Map<String, Object>> t1(@RequestParam String machineId, @RequestParam String event) {
        StateMachine<OrderStateMachineKind.State, OrderStateMachineKind.Event> machine = machineService.acquireStateMachine(machineId);
        StateMachineEventResult<OrderStateMachineKind.State, OrderStateMachineKind.Event> stateEventStateMachineEventResult = machine.sendEvent(Mono.just(MessageBuilder.withPayload(OrderStateMachineKind.Event.valueOf(event)).build())).blockLast();
        machineService.releaseStateMachine(machineId);
        return CommonResult.ok();
    }

    @GetMapping(path = "t2")
    public CommonResult<Map<String, Object>> t1(@RequestParam String event) {
        order2StateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Order2StateMachineKind.Event.valueOf(event)).build()))
                .subscribe();
        return CommonResult.ok();
    }

    @GetMapping(path = "t3")
    public CommonResult<Map<String, Object>> t3(@RequestParam Long id, @RequestParam String event) {
        persist.change(id, event);
        return CommonResult.ok();
    }

    @GetMapping(path = "testAuth")
    public CommonResult<Map<String, Object>> testAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return CommonResult.ok(Map.of());
    }
    @PreAuthorize("hasAnyAuthority('list1')")
    @GetMapping(path = "testAuthorize")
    public CommonResult<Map<String, Object>> testAuthorize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return CommonResult.ok(Map.of());
    }
}
