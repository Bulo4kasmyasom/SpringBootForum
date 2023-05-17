package com.javarush.springbootforum.validation.impl;

import com.javarush.springbootforum.validation.NumberValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NumberValidatorImpl implements ConstraintValidator<NumberValidator, Number> {

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null) return false;

        if (value instanceof Integer)
            return (Pattern.matches("^[0-9]+$", value.toString()) && value.intValue() < Integer.MAX_VALUE);
        else if (value instanceof Long)
            return (Pattern.matches("^[0-9]+$", value.toString()) && value.longValue() < Long.MAX_VALUE);
        else
            return false;
    }
}