package com.javarush.springbootforum.dto;

import lombok.Value;

import java.util.List;

@Value
public class CategoryReadDto {
    Long id;
    String title;
    String description;
    List<SubCategoryReadDto> subCategory;
}
