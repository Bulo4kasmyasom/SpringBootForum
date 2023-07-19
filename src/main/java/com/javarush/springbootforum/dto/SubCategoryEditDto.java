package com.javarush.springbootforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "SubCategoryEditDto")
public class SubCategoryEditDto {
    @Size(min = 2, max = 128)
    @Schema(description = "SubCategory title", example = "My new SubCategory title")
    String title;

    @Size(max = 512)
    @Schema(description = "SubCategory description", example = "My new SubCategory description")
    String description;
}
