package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "CategoryEditDto")
public class CategoryEditDto {
    @Size(min = 2, max = 128)
    @Schema(description = "Category title", example = "My new category title")
    String title;

    @Size(max = 512)
    @Schema(description = "Category description", example = "My new category description")
    String description;
}
