package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.mapper.CategoryReadMapper;
import com.javarush.springbootforum.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryReadMapper categoryReadMapper;

    public List<CategoryReadDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryReadMapper::map)
                .toList();
    }

    public Optional<CategoryReadDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryReadMapper::map);
    }


}