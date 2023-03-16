package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SubCategoryCreateEditDto {
    @NumberValidator
    Long categoryId;

    @Size(min = 2, max = 128)
    String title;

    @Size(max = 512)
    String description;
}
