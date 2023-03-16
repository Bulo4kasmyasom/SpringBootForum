package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.SubCategoryCreateEditDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.mapper.SubCategoryCreateEditMapper;
import com.javarush.springbootforum.mapper.SubCategoryReadMapper;
import com.javarush.springbootforum.repository.SubCategoryRepository;
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
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryReadMapper subCategoryReadMapper;
    private final SubCategoryCreateEditMapper subCategoryCreateEditMapper;

    public List<SubCategoryReadDto> findAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryReadMapper::map)
                .toList();
    }

    public Optional<SubCategoryReadDto> findById(Long id) {
        return subCategoryRepository.findById(id)
                .map(subCategoryReadMapper::map);
    }

    @Transactional
    public SubCategoryReadDto create(SubCategoryCreateEditDto subCategoryCreateEditDto) {
        return Optional.of(subCategoryCreateEditDto)
                .map(subCategoryCreateEditMapper::map)
                .map(subCategoryRepository::save)
                .map(subCategoryReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}