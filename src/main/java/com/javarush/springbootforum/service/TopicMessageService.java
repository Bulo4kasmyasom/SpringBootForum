package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.entity.TopicMessage;
import com.javarush.springbootforum.mapper.TopicMessageCreateEditMapper;
import com.javarush.springbootforum.mapper.TopicMessageReadMapper;
import com.javarush.springbootforum.repository.TopicMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicMessageService {
    private final TopicMessageRepository topicMessageRepository;
    private final TopicMessageReadMapper topicMessageReadMapper;
    private final TopicMessageCreateEditMapper topicMessageCreateEditMapper;

    public List<TopicMessageReadDto> findAll() {
        return topicMessageRepository.findAll()
                .stream()
                .map(topicMessageReadMapper::map)
                .toList();
    }

    public Page<TopicMessageReadDto> findAllByTopicId(Long id, Pageable pageable) {
        return topicMessageRepository.findAllByTopicId(id, pageable)
                .map(topicMessageReadMapper::map);
    }

    @Transactional
    public TopicMessageReadDto create(TopicMessageCreateEditDto topicMessageCreateEditDto) {
        return Optional.of(topicMessageCreateEditDto)
                .map(topicMessageCreateEditMapper::map)
                .map(topicMessageRepository::save)
                .map(topicMessageReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public TopicMessage save(TopicMessage topicMessage) {
        return Optional.of(topicMessage)
                .map(topicMessageRepository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}