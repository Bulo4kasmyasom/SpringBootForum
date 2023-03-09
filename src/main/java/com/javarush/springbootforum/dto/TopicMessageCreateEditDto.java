package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class TopicMessageCreateEditDto {
    @NumberValidator(message = "Неверный формат id у темы") // todo не выводит сообщение
    Long topicId;

    @Size(min = 2, max = 4000)
    String text;

    @NumberValidator(message = "Неверный формат id у автора")// todo не выводит сообщение
    Long authorId;
}