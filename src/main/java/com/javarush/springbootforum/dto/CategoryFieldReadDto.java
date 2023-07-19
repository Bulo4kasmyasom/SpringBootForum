package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(description = "CategoryFieldReadDto")
public class CategoryFieldReadDto {
    @Schema(description = "Category title", example = "My category title")
    String title;

    @Schema(description = "Category title", example = "My category description")
    String description;
}
