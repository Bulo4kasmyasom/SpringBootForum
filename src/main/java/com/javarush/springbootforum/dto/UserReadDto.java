package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "UserReadDto")
public class UserReadDto {
    @Schema(description = "User id", example = "1")
    private Long id;

    @Schema(description = "username", example = "Alex")
    private String username;

    @Schema(description = "image", example = "20.png")
    private String image;

    @Schema(description = "role", example = "MODERATOR")
    private String role;

    @Schema(description = "email", example = "alex@gmail.com")
    private String email;

    @Schema(description = "Last activity date", example = "15.03.2023 17:44:31")
    private String lastActivity;

    @Schema(description = "Register date", example = "15.03.2023 17:44:31")
    private String createdAt;
}
