package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.validation.EnumValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserCreateEditDto { // todo разбить обратно на 2 класса дто: create и edit
    @Size(min = 2, max = 20, message = "{validation.error.login.size}")
    String username;
    @Size(min = 2, max = 20, message = "{validation.error.password.size}")
    String password;
    @NotNull(message = "{validation.error.image.not_null}") // todo не работает проверка
    String image;
    @Email(message = "{validation.error.email}") // todo не работает проверка
    String email;

    @EnumValidator(enumClass = Role.class, message = "{validation.error.role}")
    String role;
}