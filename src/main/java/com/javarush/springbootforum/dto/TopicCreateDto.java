package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicCreateDto {
    @Size(min = 2, max = 200)
    @NotNull
    private final String topicTitle;

    @Size(min = 2, max = 4000)
    @NotNull
    private final String messageText;

    @NumberValidator
    @NotNull
    private final Long categoryId;

    //    @NumberValidator
    private final Long subCategoryId;

    @Nullable
    private Long authorId; // устанавливается сеттером после получения из UserDetails (чтобы не было подмены)

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}