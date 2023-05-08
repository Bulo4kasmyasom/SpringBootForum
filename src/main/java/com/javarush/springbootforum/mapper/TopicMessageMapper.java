package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.entity.TopicMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, SubCategoryMapper.class, BaseDtoMapper.class})
public interface TopicMessageMapper {
    TopicMessageReadDto toDto(TopicMessage topicMessage);

    @Mapping(target = "topic", source = "topicId")
    @Mapping(target = "author", source = "authorId")
    TopicMessage toEntity(TopicMessageCreateEditDto topicMessageCreateEditDto);

    TopicMessage toEntity(@MappingTarget TopicMessage topicMessage, TopicMessageCreateEditDto topicMessageCreateEditDto);
}