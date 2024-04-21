package com.sunny.order.core.kind;

import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
public @interface OrderStateOnTransition {

    OrderStateMachineKind.State[] source() default {};

    OrderStateMachineKind.State[] target() default {};

}