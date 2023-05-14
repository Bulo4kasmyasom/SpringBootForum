package com.javarush.springbootforum.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AOP {
    /*
        @within - check annotation on the class level
        within - check class type name
        this - обращается к АОП прокси
        target - обращается к исходному объекту
        @annotation - check annotation on method level
        args - check method param type
        @args - проверка аннотаций над объектом параметра (в ДТО например)
        bean - поиск по имени бина, не стандарт AspectJ, редко используется
        .. - сколько угодно значений
        * - одно любое значение

        execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)

        modifiers-pattern? - модификаторы доступа (опционально)
        ret-type-pattern - возвращаемый тип
        declaring-type-pattern? - в каком классе/слое искать (опционально)
        name-pattern(param-pattern) - имя метода и аргументы
     */


    @Pointcut("com.javarush.springbootforum.aop.BaseAop.isControllerLayer() " +
            "&& @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    @Pointcut("com.javarush.springbootforum.aop.BaseAop.isControllerLayer() " +
            "&& args(org.springframework.ui.Model, ..)")
    public void hasModelParam() {
    }

    @Pointcut("execution(public * com.javarush.springbootforum.service.*Service.findById(*))")
    public void anyFindByIdServiceLayer() {
    }


    @Before(value = "anyFindByIdServiceLayer() " +
            "&& args(id) " +
            "&& target(service) ",
            argNames = "joinPoint,id,service")
    public void addLoggingBeforeForServiceFindByIdMethod(JoinPoint joinPoint,
                                                         Object id,
                                                         Object service) {
        String methodName = joinPoint.getSignature().getName();
        log.info("In {} invoked {} method with id:{}", service, methodName, id);
    }

    @AfterReturning(value = "anyFindByIdServiceLayer() " +
            "&& target(service) ",
            returning = "result",
            argNames = "joinPoint,result,service")
    public void addLoggingAfterReturningForServiceFindByIdMethod(JoinPoint joinPoint,
                                                                 Object result,
                                                                 Object service) {
        String methodName = joinPoint.getSignature().getName();
        log.info("In {} invoked {} method and method was returned {}", service, methodName, result);
    }

}
