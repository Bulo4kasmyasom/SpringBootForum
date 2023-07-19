package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "TopicCreateDto")
public class TopicCreateDto {
    @Size(min = 2, max = 200)
    @NotNull
    @Schema(description = "Topic title", example = "My new topic title")
    private final String topicTitle;

    @Size(min = 2, max = 4000)
    @NotNull
    @Schema(description = "Message text", example = "My first message text in new topic")
    private final String messageText;

    @NumberValidator
    @NotNull
    @Schema(description = "Category id", example = "1")
    private final Long categoryId;

    //    @NumberValidator
    @Schema(description = "Subcategory id", example = "1")
    private final Long subCategoryId;

    @Nullable
    @Schema(description = "Topic author id", example = "2")
    private Long authorId; // устанавливается сеттером после получения из UserDetails (чтобы не было подмены)

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}