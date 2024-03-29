package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.validation.OnCreatable;
import com.javarush.springbootforum.validation.OnUpdatable;
import com.javarush.springbootforum.validation.RoleValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Schema(description = "UserCreateEditDto")
@FieldNameConstants
public class UserCreateEditDto {
    @Size(min = 2, max = 20, message = "{validation.error.login.size}", groups = {OnCreatable.class, OnUpdatable.class})
    @Schema(description = "Username", example = "alex")
    String username;

    @Size(min = 2, max = 20, message = "{validation.error.password.size}", groups = {OnCreatable.class})
    @Schema(description = "Password", example = "123456")
    String password;

    @Schema(description = "image", example = "5.png")
    String image;

    @Email(message = "{validation.error.email}", groups = {OnCreatable.class, OnUpdatable.class})
    @NotEmpty(message = "{validation.error.email}", groups = {OnCreatable.class, OnUpdatable.class})
    @Schema(description = "email", example = "alex@gmail.com")
    String email;

    @RoleValidator(enumClass = Role.class, message = "{validation.error.role}", groups = {OnCreatable.class, OnUpdatable.class})
    @Schema(description = "role", example = "USER")
    String role;
}