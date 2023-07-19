package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "CategoryCreateDto")
public class CategoryCreateDto {
    @NumberValidator
    @Schema(description = "Section ID", example = "1")
    Long sectionId;

    @Size(min = 2, max = 128)
    @Schema(description = "Category title", example = "My category title")
    String title;

    @Size(max = 512)
    @Schema(description = "Category description", example = "My category title")
    String description;
}
