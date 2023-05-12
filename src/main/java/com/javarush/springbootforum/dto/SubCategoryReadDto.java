package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(description = "SubCategoryReadDto")
public class SubCategoryReadDto {
    @Schema(description = "Subcategory id", example = "1")
    Long id;

    @Schema(description = "Category id", example = "2")
    Long categoryId;

    @Schema(description = "Category title", example = "My category title")
    String category;

    @Schema(description = "SubCategoryR title", example = "My subcategory title")
    String title;

    @Schema(description = "SubCategory description", example = "My subcategory description")
    String description;

    @Schema(description = "SubCategory topic count", example = "100")
    Long topicCount;
}
