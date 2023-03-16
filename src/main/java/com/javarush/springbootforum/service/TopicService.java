package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.TopicCreateDto;
import com.javarush.springbootforum.dto.TopicReadDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.Section;
import com.javarush.springbootforum.entity.TopicMessage;
import com.javarush.springbootforum.mapper.TopicCreateMapper;
import com.javarush.springbootforum.mapper.TopicReadMapper;
import com.javarush.springbootforum.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicReadMapper topicReadMapper;
    private final TopicCreateMapper topicCreateMapper;
    private final TopicMessageService topicMessageService;

    public List<TopicReadDto> findAll() {
        Sort.TypedSort<Section> sort = Sort.sort(Section.class);
        Sort sortByUpdatedAt = sort.by(Section::getUpdatedAt);
        return topicRepository.findAll(sortByUpdatedAt)
                .stream()
                .map(topicReadMapper::map)
                .toList();
    }

    public Optional<TopicReadDto> findById(Long id) {
        return topicRepository.findById(id)
                .map(topicReadMapper::map);
    }

    public Page<TopicReadDto> findAllByCategoryIdAndSubCategoryIsNull(Long id, Pageable pageable) {
        return topicRepository.findAllByCategoryIdAndSubCategoryIsNull(id, pageable)
                .map(topicReadMapper::map);
    }

    public Page<TopicReadDto> findAllByCategoryIdAndSubCategoryId(Long catId, Long subCatId, Pageable pageable) {
        return topicRepository.findAllByCategoryIdAndSubCategoryId(catId, subCatId, pageable)
                .map(topicReadMapper::map);
    }

    @Transactional
    public TopicReadDto create(UserReadDto userReadDto, TopicCreateDto topicCreateDto) {
        topicCreateDto.setAuthorId(userReadDto.getId());

        TopicMessage topicMessage = topicCreateMapper.map(topicCreateDto);
        return Optional.of(topicMessage)
                .map(topicMessageService::create)
                .map(x -> topicReadMapper.map(topicMessage.getTopic()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}