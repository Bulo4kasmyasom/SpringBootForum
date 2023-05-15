package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.validation.OnCreate;
import com.javarush.springbootforum.validation.OnUpdate;
import com.javarush.springbootforum.validation.RoleValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "UserCreateEditDto")
public class UserCreateEditDto {
    @Size(min = 2, max = 20, message = "{validation.error.login.size}")
    @Schema(description = "Username", example = "alex")
    String username;

    @Size(min = 2, max = 20, message = "{validation.error.password.size}", groups = {OnCreate.class})
    @Schema(description = "Password", example = "123456")
    String password;

    @Schema(description = "image", example = "5.png")
    String image;

    @Email(message = "{validation.error.email}") // todo не работает проверка
    @Schema(description = "email", example = "alex@gmail.com")
    String email;

    @RoleValidator(enumClass = Role.class, message = "{validation.error.role}")
    @Schema(description = "role", example = "USER")
    String role;
}