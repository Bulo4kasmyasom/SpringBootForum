package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.TopicMessage;
import com.javarush.springbootforum.mapper.TopicMessageCreateEditMapper;
import com.javarush.springbootforum.mapper.TopicMessageReadMapper;
import com.javarush.springbootforum.repository.TopicMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
        Sort.TypedSort<TopicMessage> sort = Sort.sort(TopicMessage.class);
        Sort.TypedSort<LocalDateTime> sortBy = sort.by(TopicMessage::getCreatedAt);
        return topicMessageRepository.findAll(sortBy)
                .stream()
                .map(topicMessageReadMapper::map)
                .toList();
    }

    public Page<TopicMessageReadDto> findAllByTopicId(Long id, Pageable pageable) {
        return topicMessageRepository.findAllByTopicId(id, pageable)
                .map(topicMessageReadMapper::map);
    }

    public Optional<TopicMessageReadDto> findById(Long id) {
        return topicMessageRepository.findById(id)
                .map(topicMessageReadMapper::map);
    }

    @Transactional
    public TopicMessageReadDto create(UserReadDto userReadDto, TopicMessageCreateEditDto topicMessageCreateEditDto) {
        topicMessageCreateEditDto.setAuthorId(userReadDto.getId());

        return Optional.of(topicMessageCreateEditDto)
                .map(topicMessageCreateEditMapper::map)
                .map(topicMessageRepository::save)
                .map(topicMessageReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public TopicMessage create(TopicMessage topicMessage) {
        return Optional.of(topicMessage)
                .map(topicMessageRepository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Optional<TopicMessageReadDto> update(Long id, TopicMessageCreateEditDto topicMessageCreateEditDto) {
        return topicMessageRepository.findById(id)
                .map(topicMessage -> topicMessageCreateEditMapper.map(topicMessageCreateEditDto, topicMessage))
                .map(topicMessageRepository::saveAndFlush)
                .map(topicMessageReadMapper::map);
    }
}