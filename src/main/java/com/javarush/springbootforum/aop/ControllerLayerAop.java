package com.javarush.springbootforum.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(1)
public class ControllerLayerAop {
    @Pointcut("BaseAop.isControllerLayer() || BaseAop.isRestControllerLayer() && execution(public * *.*(..))")
    public void anyMethodInControllerLayer() {
    }

    @Before(value = "anyMethodInControllerLayer() " +
            "&& args(arg) " +
            "&& target(controller) ",
            argNames = "joinPoint,arg,controller")
    public void addLoggingBeforeAnyMethodsInControllerLayer(JoinPoint joinPoint,
                                                            Object arg,
                                                            Object controller) {
        String methodName = joinPoint.getSignature().getName();
        log.info("In {} invoked {} method with arg:{}", controller, methodName, arg);
    }

    @AfterReturning(value = "anyMethodInControllerLayer() " +
            "&& target(controller) ",
            returning = "result",
            argNames = "joinPoint,result,controller")
    public void addLoggingAfterReturningAnyMethodsInControllerLayer(JoinPoint joinPoint,
                                                                    Object result,
                                                                    Object controller) {
        String methodName = joinPoint.getSignature().getName();
        log.info("In {} invoked {} method and method was returned {}", controller, methodName, result);
    }

    @AfterThrowing(value = "anyMethodInControllerLayer() && args(arg)", throwing = "e")
    public void addLoggingAfterThrowingAnyMethodsInControllerLayer(JoinPoint joinPoint, Object arg, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method " + methodName + " was throwing exception: " + e.getMessage() + " with args: " + arg);
    }

}
