package com.sunny.order.core.config;

import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.sunny.order.core.kind.OrderStateMachineKind;
import com.sunny.order.core.model.po.OrderPO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColaStateMachineConfigure {

    @Bean("colaOrderOperaMachine")
    public StateMachine<OrderStateMachineKind.State, OrderStateMachineKind.Event, OrderPO> orderOperaMachine() {
        String ORDER_OPERA = "order_opera";
        StateMachineBuilder<OrderStateMachineKind.State, OrderStateMachineKind.Event, OrderPO> builder = StateMachineBuilderFactory.create();
        //订单从初始化状态-待发货-状态-转到-关闭订单状态--用户关闭
        builder.externalTransitions()
                .fromAmong(OrderStateMachineKind.State.WAIT_PAY)
                .to(OrderStateMachineKind.State.WAIT_DELIVER)
                .on(OrderStateMachineKind.Event.PAY)
                .when(checkCondition())
                .perform((from, to, event, context) -> {

                });

        StateMachine<OrderStateMachineKind.State, OrderStateMachineKind.Event, OrderPO> orderOperaMachine = builder.build(ORDER_OPERA);

        //打印uml图
        String plantUML = orderOperaMachine.generatePlantUML();
        System.out.println(plantUML);
        return orderOperaMachine;
    }

    private Condition<OrderPO> checkCondition() {
        return (ctx) -> {
            return true;
        };
    }
}
