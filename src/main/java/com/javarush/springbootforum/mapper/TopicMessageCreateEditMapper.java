package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.entity.Topic;
import com.javarush.springbootforum.entity.TopicMessage;
import com.javarush.springbootforum.entity.User;
import com.javarush.springbootforum.repository.TopicRepository;
import com.javarush.springbootforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TopicMessageCreateEditMapper implements Mapper<TopicMessageCreateEditDto, TopicMessage> {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Override
    public TopicMessage map(TopicMessageCreateEditDto object) {
        TopicMessage topicMessage = new TopicMessage();
        copy(object, topicMessage);
        return topicMessage;
    }

    @Override
    public TopicMessage map(TopicMessageCreateEditDto fromObject, TopicMessage toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(TopicMessageCreateEditDto object, TopicMessage topicMessage) {
        User author = getAuthor(object.getAuthorId());
        Topic topic = getTopic(object.getTopicId());

        topicMessage.setText(object.getText());
        topicMessage.setAuthor(author);
        topicMessage.setTopic(topic);
    }

    private Topic getTopic(Long topicId) {
        return Optional.ofNullable(topicId)
                .flatMap(topicRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private User getAuthor(Long authorId) {
        return Optional.ofNullable(authorId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}