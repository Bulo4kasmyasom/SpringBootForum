package com.javarush.springbootforum.dto;

import lombok.Value;

@Value
public class UserReadDto {
    Long id;
    String username;
    String image;
    String role;
    String email;
    String lastActivity;
    String createdAt;
}
