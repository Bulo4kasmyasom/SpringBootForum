package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.entity.TopicMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {TopicMessageMapperBase.class, CategoryMapper.class, SubCategoryMapper.class})
public interface TopicMessageMapper {
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH:mm:ss")
    @Mapping(target = "author.createdAt", source = "author.createdAt", dateFormat = "dd.MM.yyyy HH:mm:ss")
    TopicMessageReadDto toDto(TopicMessage topicMessage);

    TopicMessageMapperBase t = Mappers.getMapper(TopicMessageMapperBase.class);

    @Mapping(target = "topic", source = "topicId", qualifiedByName = "findTopicById")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "findUserById")
    TopicMessage toEntity(TopicMessageCreateEditDto topicMessageCreateEditDto);

    TopicMessage toEntity(@MappingTarget TopicMessage topicMessage, TopicMessageCreateEditDto topicMessageCreateEditDto);
}