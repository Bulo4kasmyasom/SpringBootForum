package com.javarush.springbootforum.dto;

import lombok.Value;

import java.util.List;

@Value
public class SectionWithoutCategoryListReadDto {
    String title;
    String description;
}
