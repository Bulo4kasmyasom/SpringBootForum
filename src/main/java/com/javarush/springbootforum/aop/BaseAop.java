package com.javarush.springbootforum.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BaseAop {

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
