package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.SubCategoryCreateDto;
import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.entity.SubCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {
    List<SubCategoryReadDto> findAll();

    Optional<SubCategoryReadDto> findById(Long id);

    Optional<SubCategory> findByCategoryIdAndId(Long categoryId, Long subCategoryId);

    @Transactional
    SubCategoryReadDto create(SubCategoryCreateDto subCategoryCreateDto);

    @Transactional
    Optional<SubCategoryFieldReadDto> update(Long id, SubCategoryEditDto subCategoryEditDto);

    @Transactional
    boolean delete(Long id);
}
