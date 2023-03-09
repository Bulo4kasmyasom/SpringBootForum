package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.TopicCreateDto;
import com.javarush.springbootforum.entity.*;
import com.javarush.springbootforum.repository.CategoryRepository;
import com.javarush.springbootforum.repository.SubCategoryRepository;
import com.javarush.springbootforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TopicCreateMapper implements Mapper<TopicCreateDto, TopicMessage> {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;


    @Override
    public TopicMessage map(TopicCreateDto object) {

        User author = getAuthor(object.getAuthorId());
        Topic topic = Topic.builder()
                .title(object.getTopicTitle())
                .category(getCategory(object.getCategoryId()))
                .subCategory(getSubCategory(object.getSubCategoryId()))
                .author(author)
                .build();

        TopicMessage topicMessage = new TopicMessage();
        topicMessage.setText(object.getMessageText());
        topicMessage.setAuthor(author);
        topicMessage.setTopic(topic);

        return topicMessage;
    }

    private User getAuthor(Long authorId) {
        return Optional.ofNullable(authorId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Category getCategory(Long categoryId) {
        return Optional.ofNullable(categoryId)
                .flatMap(categoryRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private SubCategory getSubCategory(Long subCategoryId) {
        return Optional.ofNullable(subCategoryId)
                .flatMap(subCategoryRepository::findById)
                .orElse(null);
    }
}
