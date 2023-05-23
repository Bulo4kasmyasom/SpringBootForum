package com.javarush.springbootforum.service.impl;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.SubCategoryCreateDto;
import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.entity.SubCategory;
import com.javarush.springbootforum.mapper.SubCategoryMapper;
import com.javarush.springbootforum.repository.SubCategoryRepository;
import com.javarush.springbootforum.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    @Override
    public List<SubCategoryReadDto> findAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::toDto)
                .toList();
    }

    @Override
    public Optional<SubCategoryReadDto> findById(Long id) {
        return subCategoryRepository.findById(id)
                .map(subCategoryMapper::toDto);
    }

    @Override
    public Optional<SubCategory> findByCategoryIdAndId(Long categoryId, Long subCategoryId) {
        return subCategoryRepository.findByCategoryIdAndId(categoryId, subCategoryId);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public SubCategoryReadDto create(SubCategoryCreateDto subCategoryCreateDto) {
        return Optional.of(subCategoryCreateDto)
                .map(subCategoryMapper::toEntity)
                .map(subCategoryRepository::save)
                .map(subCategoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<SubCategoryFieldReadDto> update(Long id, SubCategoryEditDto subCategoryEditDto) {
        return subCategoryRepository.findById(id)
                .map(subCategory -> subCategoryMapper.toEntity(subCategory, subCategoryEditDto))
                .map(subCategoryRepository::saveAndFlush)
                .map(subCategoryMapper::toDto2);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Long id) {
        return subCategoryRepository.findById(id)
                .map(topic -> {
                    subCategoryRepository.delete(topic);
                    subCategoryRepository.flush();
                    return true;
                }).orElse(false);
    }
}