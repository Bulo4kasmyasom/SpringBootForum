package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicMessageCreateEditDto {
    @NumberValidator(message = "Неверный формат id у темы") // todo не выводит сообщение
    private final Long topicId;

    @Size(min = 2, max = 4000)
    private final String text;

    @Nullable
    private Long authorId; // устанавливается сеттером после получения из UserDetails (чтобы не было подмены)

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}