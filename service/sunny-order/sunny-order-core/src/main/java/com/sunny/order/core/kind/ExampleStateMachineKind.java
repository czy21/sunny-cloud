package com.sunny.order.core.kind;

import lombok.Getter;

public class ExampleStateMachineKind {

    @Getter
    public enum State {
        WAIT_PAY("待支付"),
        WAIT_DELIVER("待发货"),
        WAIT_RECEIVE("待收货"),
        FINISHED("已完成");
        final String label;

        State(String label) {
            this.label = label;
        }
    }

    public enum Event {
        PAY,
        DELIVER,
        RECEIVE
    }
}
