package com.javarush.springbootforum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberValidatorImpl.class)
@NotNull
public @interface NumberValidator {
    String message() default "Неверный числовой формат";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
