package com.javarush.springbootforum.service.impl;

import com.javarush.springbootforum.dto.SubCategoryCreateDto;
import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.entity.SubCategory;
import com.javarush.springbootforum.mapper.DtoMapper;
import com.javarush.springbootforum.repository.SubCategoryRepository;
import com.javarush.springbootforum.service.SubCategoryService;
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
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final DtoMapper dtoMapper;

    @Override
    public List<SubCategoryReadDto> findAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(dtoMapper::subCategoryToSubCategoryReadDto)
                .toList();
    }

    @Override
    public Optional<SubCategoryReadDto> findById(Long id) {
        return subCategoryRepository.findById(id)
                .map(dtoMapper::subCategoryToSubCategoryReadDto);
    }

    @Override
    public Optional<SubCategory> findByCategoryIdAndId(Long categoryId, Long subCategoryId) {
        return subCategoryRepository.findByCategoryIdAndId(categoryId, subCategoryId);
    }

    @Override
    @Transactional
    public SubCategoryReadDto create(SubCategoryCreateDto subCategoryCreateDto) {
        return Optional.of(subCategoryCreateDto)
                .map(dtoMapper::subCategoryCreateDtoToSubCategory)
                .map(subCategoryRepository::save)
                .map(dtoMapper::subCategoryToSubCategoryReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Optional<SubCategoryFieldReadDto> update(Long id, SubCategoryEditDto subCategoryEditDto) {
        return subCategoryRepository.findById(id)
                .map(subCategory -> dtoMapper.updateFromSubCategoryDtoToSubCategory(subCategory, subCategoryEditDto))
                .map(subCategoryRepository::saveAndFlush)
                .map(dtoMapper::subCategoryToSubCategoryFieldReadDto);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return subCategoryRepository.findById(id)
                .map(topic -> {
                    subCategoryRepository.delete(topic);
                    subCategoryRepository.flush();
                    return true;
                }).orElse(false);
    }
}