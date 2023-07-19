package com.javarush.springbootforum.service.impl;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.entity.Topic;
import com.javarush.springbootforum.entity.TopicMessage;
import com.javarush.springbootforum.entity.User;
import com.javarush.springbootforum.mapper.TopicMessageMapper;
import com.javarush.springbootforum.repository.TopicMessageRepository;
import com.javarush.springbootforum.repository.TopicRepository;
import com.javarush.springbootforum.service.TopicMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicMessageServiceImpl implements TopicMessageService {

    private final TopicMessageRepository topicMessageRepository;
    private final TopicRepository topicRepository;
    private final TopicMessageMapper topicMessageMapper;

    @Override
    public List<TopicMessageReadDto> findAll() {
        Sort.TypedSort<TopicMessage> sort = Sort.sort(TopicMessage.class);
        Sort.TypedSort<LocalDateTime> sortBy = sort.by(TopicMessage::getCreatedAt);
        return topicMessageRepository.findAll(sortBy)
                .stream()
                .map(topicMessageMapper::toDto)
                .toList();
    }

    @Override
    public Page<TopicMessageReadDto> findAllByTopicId(Long id, Pageable pageable) {
        return topicMessageRepository.findAllByTopicId(id, pageable)
                .map(topicMessageMapper::toDto);
    }

    @Override
    public Optional<TopicMessageReadDto> findById(Long id) {
        return topicMessageRepository.findById(id)
                .map(topicMessageMapper::toDto);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('USER','MODERATOR','ADMIN')")
    public TopicMessageReadDto create(Long authorId, TopicMessageCreateEditDto topicMessageCreateEditDto) {
        topicMessageCreateEditDto.setAuthorId(authorId);

        return Optional.of(topicMessageCreateEditDto)
                .map(topicMessageMapper::toEntity)
                .map(topicMessageRepository::save)
                .map(topicMessageMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Topic message not found"));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('USER','MODERATOR','ADMIN')")
    public TopicMessage create(TopicMessage topicMessage) {
        return Optional.of(topicMessage)
                .map(topicMessageRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Topic message not found"));
    }

    @Override
    @Transactional
    @PreAuthorize("@topicMessageSecurityExpression.canUserEditDeleteMessage(#topicMessageId, 'MODERATOR','ADMIN')")
    // todo проверить а своё ли он сообщение обновляет - USER
    public Optional<TopicMessageReadDto> update(Long topicMessageId, TopicMessageCreateEditDto topicMessageCreateEditDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        topicMessageCreateEditDto.setAuthorId(user.getId());

        return topicMessageRepository.findById(topicMessageId)
                .map(topicMessage -> topicMessageMapper.toEntity(topicMessage, topicMessageCreateEditDto))
                .map(topicMessageRepository::saveAndFlush)
                .map(topicMessageMapper::toDto);
    }

    @Override
    @Transactional
    @PreAuthorize("@topicMessageSecurityExpression.canUserEditDeleteMessage(#id, 'MODERATOR','ADMIN')")
    public boolean delete(Long id) {
        return topicMessageRepository.findById(id)
                .map(topicMessage -> {
                    topicMessageRepository.delete(topicMessage);
                    topicMessageRepository.flush();
                    Topic topic = topicMessage.getTopic();
                    Long topicId = topic.getId();
                    if (topicMessageRepository.countTopicMessageByTopicId(topicId) == 0) {
                        // delete topic if count of messages == 0
                        topicRepository.delete(topic);
                        topicRepository.flush();
                    }
                    return true;
                }).orElse(false);
    }
}