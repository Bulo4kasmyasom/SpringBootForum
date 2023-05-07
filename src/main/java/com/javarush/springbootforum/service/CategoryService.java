package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.CategoryCreateDto;
import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.mapper.DtoMapper;
import com.javarush.springbootforum.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService implements CategoryServiceInterface {
    private final CategoryRepository categoryRepository;
    private final DtoMapper dtoMapper;

    @Override
    public List<CategoryReadDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(dtoMapper::categoryToCategoryReadDto)
                .toList();
    }

    @Override
    public Optional<CategoryReadDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(dtoMapper::categoryToCategoryReadDto);
    }

    public Optional<Category> findByIdAndReturnCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional
    public CategoryReadDto create(CategoryCreateDto categoryCreateDto) {
        return Optional.of(categoryCreateDto)
                .map(dtoMapper::categoryCreateDtoToCategory)
                .map(categoryRepository::save)
                .map(dtoMapper::categoryToCategoryReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Optional<CategoryFieldReadDto> update(Long id, CategoryEditDto categoryEditDto) {
        return categoryRepository.findById(id)
                .map(category -> dtoMapper.updateFromCategoryDtoToCategory(category, categoryEditDto))
                .map(categoryRepository::saveAndFlush)
                .map(dtoMapper::categoryToCategoryFieldReadDto);
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