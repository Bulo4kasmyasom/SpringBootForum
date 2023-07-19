package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.TopicCreateDto;
import com.javarush.springbootforum.dto.TopicEditDto;
import com.javarush.springbootforum.dto.TopicFieldReadDto;
import com.javarush.springbootforum.dto.TopicReadDto;
import com.javarush.springbootforum.entity.Topic;
import com.javarush.springbootforum.entity.TopicMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TopicMapperBase.class})
public interface TopicMapper {
    @Mapping(target = "subCategory.category", source = "subCategory.category.title")
    @Mapping(target = "createdAt", dateFormat = "dd.MM.yyyy HH:mm:ss")
    TopicReadDto toDto(Topic topic);

    TopicFieldReadDto toDto2(Topic topic);

    Topic toEntity(@MappingTarget Topic topic, TopicEditDto topicEditDto);

    @Mapping(target = "topic.title", source = "topicTitle")
    @Mapping(target = "topic.category", source = "categoryId", qualifiedByName = "findCategoryById")
    @Mapping(target = "topic.subCategory", source = "subCategoryId", qualifiedByName = "findSubCategoryById")
    @Mapping(target = "topic.author", source = "authorId", qualifiedByName = "findUserByIdForTopicAuthor")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "findUserByIdForTopicMessageAuthor")
    @Mapping(target = "text", source = "messageText")
    TopicMessage toEntity(TopicCreateDto topic);

}
