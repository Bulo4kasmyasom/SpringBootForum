package com.javarush.springbootforum.mapper;


import com.javarush.springbootforum.entity.Topic;
import com.javarush.springbootforum.entity.User;
import com.javarush.springbootforum.repository.TopicRepository;
import com.javarush.springbootforum.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Mapper
public class TopicMessageMapperBase {
    private TopicRepository topicRepository;
    private UserRepository userRepository;

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Named("findTopicById")
    protected Topic findTopicById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(topicRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Named("findUserById")
    protected User findUserById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}