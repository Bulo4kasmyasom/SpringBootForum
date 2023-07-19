package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "SectionCreateEditDto")
public class SectionCreateEditDto {

    @Size(min = 2, max = 128)
    @Schema(description = "Section title", example = "My section title")
    String title;

    @Size(max = 512)
    @Schema(description = "Section description", example = "My section description")
    String description;

}