package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(description = "SectionWithoutCategoryListReadDto")
public class SectionWithoutCategoryListReadDto {

    @Schema(description = "Section title", example = "My title")
    String title;

    @Schema(description = "Section description", example = "My description")
    String description;
}
