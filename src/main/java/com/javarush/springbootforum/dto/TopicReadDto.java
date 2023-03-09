package com.javarush.springbootforum.dto;

import lombok.Value;

@Value
public class TopicReadDto {
    Long id;
    CategoryReadDto category;
    SubCategoryReadDto subCategory;
    String title;
    UserReadDto author;
    String createdAt;
}