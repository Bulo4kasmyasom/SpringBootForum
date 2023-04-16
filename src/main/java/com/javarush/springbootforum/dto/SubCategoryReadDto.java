package com.javarush.springbootforum.dto;

import lombok.Value;

@Value
public class SubCategoryReadDto {
    Long id;
    Long categoryId;
    String category;
    String title;
    String description;
    Long topicCount;
}
