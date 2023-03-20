package com.javarush.springbootforum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TopicEditDto {
    @Size(min = 2, max = 200)
    @NotNull
    private final String title;

    @JsonCreator
    public TopicEditDto(String title) {
        this.title = title;
    }

//    todo
//    Without @JsonCreator:
//    Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot construct
//    instance of `com.javarush.springbootforum.dto.TopicEditDto` (although at least one Creator exists): cannot
//    deserialize from Object value (no delegate- or property-based Creator)]

}
