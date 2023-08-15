package com.javarush.springbootforum.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BaseAop {

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isRestControllerLayer() {
    }

    @Pointcut("within(com..service.*Service)")
    public void isServiceLayer() {
    }

    @Pointcut("this(org.springframework.stereotype.Repository)")
    public void isRepositoryLayer() {
    }
}
