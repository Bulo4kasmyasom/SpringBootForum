package com.javarush.springbootforum.service.impl;

import com.javarush.springbootforum.dto.*;
import com.javarush.springbootforum.entity.*;
import com.javarush.springbootforum.mapper.DtoMapper;
import com.javarush.springbootforum.repository.TopicRepository;
import com.javarush.springbootforum.service.SubCategoryService;
import com.javarush.springbootforum.service.TopicMessageService;
import com.javarush.springbootforum.service.TopicService;
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
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final DtoMapper dtoMapper;
    private final TopicMessageService topicMessageService;
    private final SubCategoryService subCategoryService;
    private final CategoryServiceImpl categoryServiceImpl;

    @Override
    public List<TopicReadDto> findAll() {
        Sort.TypedSort<Section> sort = Sort.sort(Section.class);
        Sort sortByUpdatedAt = sort.by(Section::getUpdatedAt);
        return topicRepository.findAll(sortByUpdatedAt)
                .stream()
                .map(dtoMapper::topicToTopicReadDto)
                .toList();
    }

    @Override
    public Optional<TopicReadDto> findById(Long id) {
        return topicRepository.findById(id)
                .map(dtoMapper::topicToTopicReadDto);
    }

    @Override
    public Page<TopicReadDto> findAllByCategoryIdAndSubCategoryIsNull(Long id, Pageable pageable) {
        return topicRepository.findAllByCategoryIdAndSubCategoryIsNull(id, pageable)
                .map(dtoMapper::topicToTopicReadDto);
    }

    @Override
    public Page<TopicReadDto> findAllByCategoryIdAndSubCategoryId(Long catId, Long subCatId, Pageable pageable) {
        return topicRepository.findAllByCategoryIdAndSubCategoryId(catId, subCatId, pageable)
                .map(dtoMapper::topicToTopicReadDto);
    }

    @Override
    @Transactional
    public TopicReadDto create(UserReadDto userReadDto, TopicCreateDto topicCreateDto) {
        topicCreateDto.setAuthorId(userReadDto.getId());

        TopicMessage topicMessage = dtoMapper.topicCreateDtoToTopicMessage(topicCreateDto);
        return Optional.of(topicMessage)
                .map(topicMessageService::create)
                .map(message -> dtoMapper.topicToTopicReadDto(message.getTopic()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Optional<TopicFieldReadDto> update(Long topicId, TopicEditDto topicEditDto) {
        return topicRepository.findById(topicId)
                .map(topic -> dtoMapper.updateFromTopicDtoToTopic(topic, topicEditDto))
                .map(topicRepository::saveAndFlush)
                .map(dtoMapper::topicToTopicFieldReadDto);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    topicRepository.delete(topic);
                    topicRepository.flush();
                    return true;
                }).orElse(false);
    }

    @Override
    @Transactional
    public boolean moveTopicsInCategory(Long catId, Long[] topicsIds) {
        Category newCategory = categoryServiceImpl.findByIdAndReturnCategory(catId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Topic> topics = getTopicsForMove(topicsIds, newCategory, null); // null т.к. нет подкатегории

        // todo  N + 1 при обновлении, batch не работает из application.yaml
        return topicRepository.saveAllAndFlush(topics).size() > 0;
    }

    @Override
    @Transactional
    public boolean moveTopicsInSubCategory(Long catId, Long subCatId, Long[] topicsIds) {
        Category newCategory = categoryServiceImpl.findByIdAndReturnCategory(catId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SubCategory newSubCategory = subCategoryService.findByCategoryIdAndId(catId, subCatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Topic> topics = getTopicsForMove(topicsIds, newCategory, newSubCategory);

        // todo  N + 1 при обновлении, batch не работает из application.yaml
        return topicRepository.saveAllAndFlush(topics).size() > 0;
    }

    @Override
    @Transactional
    public boolean massDelete(Long[] topicsIds) {
        if (topicsIds.length > 0) {
            topicRepository.deleteAllByIdInBatch(List.of(topicsIds));
            topicRepository.flush();
            return topicRepository.existsById(topicsIds[0]);
        }
        return false;
    }


    private List<Topic> getTopicsForMove(Long[] topicsIds, Category newCategory, SubCategory newSubCategory) {
        // todo много запросов, нужно уменьшать их кол-во и подумать над адекватностью кода ниже. Если выделить
        //  в отдельные переменные категории и подкатегории, то некорректно работает код,
        //  поэтому геттеры и сеттеры
        return topicRepository.findTopicsByIdIn(List.of(topicsIds))
                .stream()
                .peek(topic -> {
                    long numToSetTopicCounter = 1;

                    topic.getCategory().setTopicCount(topic.getCategory().getTopicCount() - numToSetTopicCounter);
                    if (topic.getSubCategory() != null)
                        topic.getSubCategory().setTopicCount(topic.getSubCategory().getTopicCount() - numToSetTopicCounter);

                    topic.setCategory(newCategory);
                    topic.setSubCategory(newSubCategory);

                    topic.getCategory().setTopicCount(newCategory.getTopicCount() + numToSetTopicCounter);
                    if (topic.getSubCategory() != null && newSubCategory != null)
                        topic.getSubCategory().setTopicCount(newSubCategory.getTopicCount() + numToSetTopicCounter);

                }).toList();
    }

}