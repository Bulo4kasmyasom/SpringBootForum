package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CategoryCreateEditDto {
    @NumberValidator
    Long sectionId;

    @Size(min = 2, max = 128)
    String title;

    @Size(max = 512)
    String description;
}
