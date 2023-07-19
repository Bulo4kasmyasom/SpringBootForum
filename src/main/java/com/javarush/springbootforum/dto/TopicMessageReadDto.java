package com.javarush.springbootforum.dto;

import lombok.Value;

@Value
public class TopicMessageReadDto {
    Long id;
    TopicReadDto topic;
    String text;
    UserReadDto author;
    String createdAt;
    String updatedAt;
}