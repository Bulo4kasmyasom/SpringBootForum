package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.TopicFieldReadDto;
import com.javarush.springbootforum.entity.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class TopicFieldReadMapper implements Mapper<Topic, TopicFieldReadDto> {
    @Override
    public TopicFieldReadDto map(Topic object) {

        return new TopicFieldReadDto(
                object.getTitle()
        );
    }

}