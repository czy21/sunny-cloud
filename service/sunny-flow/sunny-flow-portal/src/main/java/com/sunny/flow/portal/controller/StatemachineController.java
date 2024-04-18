package com.sunny.flow.portal.controller;

import com.sunny.flow.core.kind.MachineKind;
import com.sunny.framework.core.model.CommonResult;
import com.sunny.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
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
    StateMachineService<MachineKind.State, MachineKind.Event> machineService;

    @GetMapping(path = "t1")
    public CommonResult<Map<String, Object>> t1(@RequestParam String machineId, @RequestParam String event) {
        StateMachine<MachineKind.State, MachineKind.Event> machine = machineService.acquireStateMachine(machineId);
        StateMachineEventResult<MachineKind.State, MachineKind.Event> stateEventStateMachineEventResult = machine.sendEvent(Mono.just(MessageBuilder.withPayload(MachineKind.Event.valueOf(event)).build())).blockLast();
        machineService.releaseStateMachine(machineId);
        return CommonResult.ok();
    }
}
