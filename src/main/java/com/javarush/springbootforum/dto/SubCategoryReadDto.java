package com.javarush.springbootforum.dto;

import lombok.Value;

@Value
public class SubCategoryReadDto {
    Long id;
    String category;
    String title;
    String description;
}
