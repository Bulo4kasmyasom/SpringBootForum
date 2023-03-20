package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.SubCategoryCreateDto;
import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.mapper.SubCategoryCreateMapper;
import com.javarush.springbootforum.mapper.SubCategoryEditMapper;
import com.javarush.springbootforum.mapper.SubCategoryFieldReadMapper;
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
    private final SubCategoryCreateMapper subCategoryCreateMapper;
    private final SubCategoryEditMapper subCategoryEditMapper;
    private final SubCategoryFieldReadMapper subCategoryFieldReadMapper;

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
    public SubCategoryReadDto create(SubCategoryCreateDto subCategoryCreateDto) {
        return Optional.of(subCategoryCreateDto)
                .map(subCategoryCreateMapper::map)
                .map(subCategoryRepository::save)
                .map(subCategoryReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Optional<SubCategoryFieldReadDto> update(Long id, SubCategoryEditDto subCategoryEditDto) {
        return subCategoryRepository.findById(id)
                .map(section -> subCategoryEditMapper.map(subCategoryEditDto, section))
                .map(subCategoryRepository::saveAndFlush)
                .map(subCategoryFieldReadMapper::map);
    }
}