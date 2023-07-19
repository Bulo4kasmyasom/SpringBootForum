package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Value
@Schema(description = "SectionReadDto")
public class SectionReadDto {

    @Schema(description = "Section id", example = "1")
    Long id;

    @Schema(description = "Section title", example = "My title")
    String title;

    @Schema(description = "Section description", example = "My super description")
    String description;

    @Schema(description = "Category list", example = "categoryList[]")
    List<CategoryReadDto> categoryList;
}
