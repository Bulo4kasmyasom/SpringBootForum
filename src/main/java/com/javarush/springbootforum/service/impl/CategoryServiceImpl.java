package com.javarush.springbootforum.service.impl;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.CategoryCreateDto;
import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.mapper.CategoryMapper;
import com.javarush.springbootforum.repository.CategoryRepository;
import com.javarush.springbootforum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryReadDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public Optional<CategoryReadDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto);
    }

    public Optional<Category> findByIdAndReturnCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional
    public CategoryReadDto create(CategoryCreateDto categoryCreateDto) {
        return Optional.of(categoryCreateDto)
                .map(categoryMapper::toEntity)
                .map(categoryRepository::save)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    @Transactional
    public Optional<CategoryFieldReadDto> update(Long id, CategoryEditDto categoryEditDto) {
        return categoryRepository.findById(id)
                .map(category -> categoryMapper.toEntity(category, categoryEditDto))
                .map(categoryRepository::saveAndFlush)
                .map(categoryMapper::toDto2);
    }

    @Transactional
    public boolean delete(Long id) {
        return categoryRepository.findById(id)
                .map(topic -> {
                    categoryRepository.delete(topic);
                    categoryRepository.flush();
                    return true;
                }).orElse(false);
    }
}