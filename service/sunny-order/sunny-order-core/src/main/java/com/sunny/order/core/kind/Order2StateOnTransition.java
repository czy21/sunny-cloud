package com.sunny.order.core.kind;

import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
public @interface Order2StateOnTransition {

    Order2StateMachineKind.State[] source() default {};

    Order2StateMachineKind.State[] target() default {};

}