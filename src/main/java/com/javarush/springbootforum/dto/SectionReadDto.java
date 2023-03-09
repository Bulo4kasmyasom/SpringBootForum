package com.javarush.springbootforum.dto;

import lombok.Value;

import java.util.List;

@Value
public class SectionReadDto {
    Long id;
    String title;
    String description;
    List<CategoryReadDto> categoryList;
}
