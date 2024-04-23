/*
 * Copyright 2015-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunny.order.core.config;

import com.sunny.order.core.kind.Order2StateMachineKind;
import com.sunny.order.core.model.po.OrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

public class Persist {

    private final PersistStateMachineHandler handler;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final PersistStateMachineHandler.PersistStateChangeListener listener = new LocalPersistStateChangeListener();

    public Persist(PersistStateMachineHandler handler) {
        this.handler = handler;
        this.handler.addPersistStateChangeListener(listener);
    }

    //tag::snippetB[]
    public void change(Long id, String event) {
        OrderPO o = jdbcTemplate.queryForObject("select id, state from `order` where id = ?",
                (rs, rowNum) -> {
                    OrderPO p = new OrderPO();
                    p.setId(rs.getLong("id"));
                    p.setState(rs.getString("state"));
                    return p;
                }, id);
        handler.handleEventWithStateReactively(
                        MessageBuilder.withPayload(Order2StateMachineKind.Event.valueOf(event)).setHeader("order", id).build(), Order2StateMachineKind.State.valueOf(o.getState()))
                .subscribe();
    }

    //end::snippetB[]

    //tag::snippetC[]
    private class LocalPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {


        @Override
        public void onPersist(State<Order2StateMachineKind.State, Order2StateMachineKind.Event> state, Message<Order2StateMachineKind.Event> message, Transition<Order2StateMachineKind.State, Order2StateMachineKind.Event> transition, StateMachine<Order2StateMachineKind.State, Order2StateMachineKind.Event> stateMachine) {
            if (message != null && message.getHeaders().containsKey("order")) {
                Long order = message.getHeaders().get("order", Long.class);
                jdbcTemplate.update("update `order` set state = ? where id = ?", state.getId().name(), order);
            }
        }
    }
//end::snippetC[]

}
