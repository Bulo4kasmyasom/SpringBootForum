package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "TopicMessageCreateEditDto")
public class TopicMessageCreateEditDto {
    @NumberValidator(message = "Неверный формат id у темы") // todo не выводит сообщение
    @Schema(description = "Topic id", example = "1")
    private final Long topicId;

    @Size(min = 2, max = 4000)
    @Schema(description = "Topic text", example = "My topic text")
    private final String text;

    @Nullable
    @Schema(description = "Topic author id", example = "2")
    private Long authorId; // устанавливается сеттером после получения из UserDetails (чтобы не было подмены)

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}