package com.javarush.springbootforum.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(2)
public class ServiceLayerAop {
    @Pointcut("BaseAop.isServiceLayer() && execution(public * *.*(..))")
    public void anyMethodInServiceLayer() {
    }

    @Before(value = "anyMethodInServiceLayer() " +
            "&& args(arg) " +
            "&& target(service) ",
            argNames = "joinPoint,arg,service")
    public void addLoggingBeforeAnyMethodsInServiceLayer(JoinPoint joinPoint,
                                                         Object arg,
                                                         Object service) {
        String methodName = joinPoint.getSignature().getName();
        log.info("In {} invoked {} method with arg:{}", service, methodName, arg);
    }

    @AfterReturning(value = "anyMethodInServiceLayer() " +
            "&& target(service) ",
            returning = "result",
            argNames = "joinPoint,result,service")
    public void addLoggingAfterReturningAnyMethodsInServiceLayer(JoinPoint joinPoint,
                                                                 Object result,
                                                                 Object service) {
        String methodName = joinPoint.getSignature().getName();
        log.info("In {} invoked {} method and method was returned {}", service, methodName, result);
    }

    @AfterThrowing(value = "anyMethodInServiceLayer() && args(arg)", throwing = "e")
    public void addLoggingAfterThrowingAnyMethodsInServiceLayer(JoinPoint joinPoint, Object arg, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method " + methodName + " was throwing exception: " + e.getMessage() + " with args: " + arg);
    }

}
