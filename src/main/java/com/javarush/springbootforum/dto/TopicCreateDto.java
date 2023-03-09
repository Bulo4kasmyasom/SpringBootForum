package com.javarush.springbootforum.dto;

import com.javarush.springbootforum.validation.NumberValidator;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopicCreateDto {
    @Size(min = 2, max = 200)
    private String topicTitle;

    @Size(min = 2, max = 4000)
    private String messageText;

    @NumberValidator
    private Long categoryId;

//    @NumberValidator
    private Long subCategoryId;

    //    @NumberValidator
//    @Nullable
    private Long authorId;

}
