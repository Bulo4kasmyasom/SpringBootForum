package com.javarush.springbootforum.validation;

import com.javarush.springbootforum.entity.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoleValidatorImpl implements ConstraintValidator<RoleValidator, String> {

    private List<String> rolesList;

    @Override
    public void initialize(RoleValidator annotation) {
        rolesList = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // роль по умолчанию будет установлена в маппере. Если всегда нужна роль, то вернуть false в строке ниже.
        if (value == null) value = Role.USER.name();
        return this.rolesList.contains(value);
    }
}
