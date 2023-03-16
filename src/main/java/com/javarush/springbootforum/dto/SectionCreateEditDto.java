package com.javarush.springbootforum.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SectionCreateEditDto {

    @Size(min = 2, max = 128)
    String title;

    @Size(max = 512)
    String description;

}