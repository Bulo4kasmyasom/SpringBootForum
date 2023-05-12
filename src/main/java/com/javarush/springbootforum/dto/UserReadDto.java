package com.javarush.springbootforum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReadDto {
    private Long id;
    private String username;
    private String image;
    private String role;
    private String email;
    private String lastActivity;
    private String createdAt;
}
