package com.javarush.springbootforum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidatorImpl.class)
//@NotNull // Если раскомментировать, то null не пройдёт, поле должно существовать и не может быть null
public @interface RoleValidator {
    Class<? extends Enum<?>> enumClass();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
