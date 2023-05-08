package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.validation.OnCreate;
import com.javarush.springbootforum.validation.OnUpdate;
import com.javarush.springbootforum.validation.RoleValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserCreateEditDto { // todo разбить обратно на 2 класса дто: create и edit
    @Size(min = 2, max = 20, message = "{validation.error.login.size}")
    String username;
    @Size(min = 2, max = 20, message = "{validation.error.password.size}", groups = {OnCreate.class, OnUpdate.class})
    String password;

    String image;

    @Email(message = "{validation.error.email}") // todo не работает проверка
    String email;

    @RoleValidator(enumClass = Role.class, message = "{validation.error.role}")
    String role;
}