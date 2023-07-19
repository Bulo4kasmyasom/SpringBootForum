package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Schema(description = "TopicMessageCreateEditDto")
public class TopicMessageCreateEditDto {
    @NumberValidator(message = "Неверный формат id у темы")
    // todo вместо сообщения пишет: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; For input string: "s"
    @Schema(description = "Topic id", example = "1")
    private final Long topicId;

    @Size(min = 2, max = 4000)
    @Schema(description = "Topic text", example = "My topic text")
    private final String text;

    @Setter
    @Nullable
    @Schema(description = "Topic author id", example = "2")
    private Long authorId; // устанавливается сеттером после получения из UserDetails (чтобы не было подмены)
}