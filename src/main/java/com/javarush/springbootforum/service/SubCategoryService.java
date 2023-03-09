package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.mapper.SubCategoryMapper;
import com.javarush.springbootforum.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    public List<SubCategoryReadDto> findAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::map)
                .toList();
    }

    public Optional<SubCategoryReadDto> findById(Long id) {
        return subCategoryRepository.findById(id)
                .map(subCategoryMapper::map);
    }


}