package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.TopicEditDto;
import com.javarush.springbootforum.entity.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopicEditMapper implements Mapper<TopicEditDto, Topic> {

    @Override
    public Topic map(TopicEditDto object) {
        return null;
    }

    @Override
    public Topic map(TopicEditDto fromObject, Topic toObject) {
        toObject.setTitle(fromObject.getTitle());
        return toObject;
    }

}
