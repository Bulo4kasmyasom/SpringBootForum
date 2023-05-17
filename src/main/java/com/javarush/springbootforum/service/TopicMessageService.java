package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.entity.TopicMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TopicMessageService {
    List<TopicMessageReadDto> findAll();

    Page<TopicMessageReadDto> findAllByTopicId(Long id, Pageable pageable);

    Optional<TopicMessageReadDto> findById(Long id);

    @Transactional
    TopicMessageReadDto create(Long authorId, TopicMessageCreateEditDto topicMessageCreateEditDto);

    @Transactional
    TopicMessage create(TopicMessage topicMessage);

    @Transactional
    Optional<TopicMessageReadDto> update(Long userId, Long id, TopicMessageCreateEditDto topicMessageCreateEditDto);

    @Transactional
    boolean delete(Long id);
}
