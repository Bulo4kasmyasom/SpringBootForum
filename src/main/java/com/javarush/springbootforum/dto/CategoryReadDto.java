package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Value
@Schema(description = "CategoryReadDto")
public class CategoryReadDto {
    @Schema(description = "Category id", example = "1")
    Long id;

    @Schema(description = "Category title", example = "My category title")
    String title;

    @Schema(description = "Category description", example = "My category description")
    String description;

    @Schema(description = "subCategory list", example = "subCategory[]")
    List<SubCategoryReadDto> subCategory;

    @Schema(description = "Category topic count", example = "10")
    Long topicCount;
}
