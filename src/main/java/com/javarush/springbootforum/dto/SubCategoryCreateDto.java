package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "SubCategoryCreateDto")
public class SubCategoryCreateDto {
    @NumberValidator
    @Schema(description = "Category id", example = "1")
    Long categoryId;

    @Size(min = 2, max = 128)
    @Schema(description = "SubCategory title", example = "My new category title")
    String title;

    @Size(max = 512)
    @Schema(description = "SubCategory description", example = "My new category description")
    String description;
}
