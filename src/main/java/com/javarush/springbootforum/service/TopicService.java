package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.*;
import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.entity.SubCategory;
import com.javarush.springbootforum.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    List<TopicReadDto> findAll();

    Optional<TopicReadDto> findById(Long id);

    Page<TopicReadDto> findAllByCategoryIdAndSubCategoryIsNull(Long id, Pageable pageable);

    Page<TopicReadDto> findAllByCategoryIdAndSubCategoryId(Long catId, Long subCatId, Pageable pageable);

    @Transactional
    TopicReadDto create(UserReadDto userReadDto, TopicCreateDto topicCreateDto);

    @Transactional
    Optional<TopicFieldReadDto> update(Long topicId, TopicEditDto topicEditDto);

    @Transactional
    boolean delete(Long id);

    @Transactional
    boolean moveTopicsInCategory(Long catId, Long[] topicsIds);

    @Transactional
    boolean moveTopicsInSubCategory(Long catId, Long subCatId, Long[] topicsIds);


    @Transactional
    boolean massDelete(Long[] topicsIds);
}
