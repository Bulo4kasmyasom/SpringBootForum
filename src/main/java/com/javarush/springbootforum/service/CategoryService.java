package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.CategoryCreateDto;
import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.dto.CategoryReadDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryReadDto> findAll();

    Optional<CategoryReadDto> findById(Long id);

    @Transactional
    CategoryReadDto create(CategoryCreateDto categoryCreateDto);

    @Transactional
    Optional<CategoryFieldReadDto> update(Long id, CategoryEditDto categoryEditDto);

    @Transactional
    boolean delete(Long id);
}
