package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.CategoryCreateDto;
import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.mapper.CategoryCreateMapper;
import com.javarush.springbootforum.mapper.CategoryEditMapper;
import com.javarush.springbootforum.mapper.CategoryFieldReadMapper;
import com.javarush.springbootforum.mapper.CategoryReadMapper;
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
    private final CategoryReadMapper categoryReadMapper;
    private final CategoryCreateMapper categoryCreateMapper;
    private final CategoryEditMapper categoryEditMapper;
    private final CategoryFieldReadMapper categoryFieldReadMapper;

    @Override
    public List<CategoryReadDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryReadMapper::map)
                .toList();
    }

    @Override
    public Optional<CategoryReadDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryReadMapper::map);
    }

    @Override
    @Transactional
    public CategoryReadDto create(CategoryCreateDto categoryCreateDto) {
        return Optional.of(categoryCreateDto)
                .map(categoryCreateMapper::map)
                .map(categoryRepository::save)
                .map(categoryReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Optional<CategoryFieldReadDto> update(Long id, CategoryEditDto categoryEditDto) {
        return categoryRepository.findById(id)
                .map(section -> categoryEditMapper.map(categoryEditDto, section))
                .map(categoryRepository::saveAndFlush)
                .map(categoryFieldReadMapper::map);
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