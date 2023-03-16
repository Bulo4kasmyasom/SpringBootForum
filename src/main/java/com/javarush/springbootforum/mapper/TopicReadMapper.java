package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.dto.TopicReadDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TopicReadMapper implements Mapper<Topic, TopicReadDto> {

    private final SubCategoryReadMapper subCategoryReadMapper;
    private final CategoryReadMapper categoryReadMapper;
    private final UserReadMapper userReadMapper;

    @Override
    public TopicReadDto map(Topic object) {

        UserReadDto userReadDto = userReadMapper.map(object.getAuthor());
        CategoryReadDto categoryReadDto = categoryReadMapper.map(object.getCategory());

        SubCategoryReadDto subCategory = (object.getSubCategory() != null) ? subCategoryReadMapper.map(object.getSubCategory()) : null;

        return new TopicReadDto(
                object.getId(),
                categoryReadDto,
                subCategory,
                object.getTitle(),
                userReadDto,
                object.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")), // TODO вынести в утиль, есть где-то ещё такая строка
                object.getMessageCount()
        );
    }

}