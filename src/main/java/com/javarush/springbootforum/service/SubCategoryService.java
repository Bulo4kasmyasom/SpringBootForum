package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.SubCategoryCreateDto;
import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.entity.SubCategory;
import com.javarush.springbootforum.mapper.DtoMapper;
import com.javarush.springbootforum.mapper.SubCategoryCreateMapper;
import com.javarush.springbootforum.mapper.SubCategoryEditMapper;
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
    private final DtoMapper dtoMapper;
    private final SubCategoryCreateMapper subCategoryCreateMapper;
    private final SubCategoryEditMapper subCategoryEditMapper;

    public List<SubCategoryReadDto> findAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(dtoMapper::subCategoryToSubCategoryReadDto)
                .toList();
    }

    public Optional<SubCategoryReadDto> findById(Long id) {
        return subCategoryRepository.findById(id)
                .map(dtoMapper::subCategoryToSubCategoryReadDto);
    }

    public Optional<SubCategory> findByCategoryIdAndId(Long categoryId, Long subCategoryId) {
        return subCategoryRepository.findByCategoryIdAndId(categoryId, subCategoryId);
    }

    @Transactional
    public SubCategoryReadDto create(SubCategoryCreateDto subCategoryCreateDto) {
        return Optional.of(subCategoryCreateDto)
                .map(subCategoryCreateMapper::map)
                .map(subCategoryRepository::save)
                .map(dtoMapper::subCategoryToSubCategoryReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Optional<SubCategoryFieldReadDto> update(Long id, SubCategoryEditDto subCategoryEditDto) {
        return subCategoryRepository.findById(id)
                .map(section -> subCategoryEditMapper.map(subCategoryEditDto, section))
                .map(subCategoryRepository::saveAndFlush)
                .map(dtoMapper::subCategoryToSubCategoryFieldReadDto);
    }

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