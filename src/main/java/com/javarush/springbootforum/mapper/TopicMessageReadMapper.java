package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.dto.TopicReadDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.TopicMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TopicMessageReadMapper implements Mapper<TopicMessage, TopicMessageReadDto> {
    private final UserReadMapper userReadMapper;
    private final TopicReadMapper topicReadMapper;

    @Override
    public TopicMessageReadDto map(TopicMessage object) {

        UserReadDto userReadDto = userReadMapper.map(object.getAuthor());
        TopicReadDto topicReadDto = topicReadMapper.map(object.getTopic());

        return new TopicMessageReadDto(
                object.getId(),
                topicReadDto,
                object.getText(),
                userReadDto,
                object.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")), // TODO вынести в утиль, есть где-то ещё такая строка
                (object.getUpdatedAt()!=null)?object.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")) : null // TODO вынести в утиль, есть где-то ещё такая строка
        );
    }

}