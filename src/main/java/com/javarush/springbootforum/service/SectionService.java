package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.dto.SectionReadDto;
import com.javarush.springbootforum.dto.SectionWithoutCategoryListReadDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SectionService {
    List<SectionReadDto> findAll();

    Optional<SectionReadDto> findById(Long id);

    @Transactional
    SectionReadDto create(SectionCreateEditDto sectionCreateEditDto);

    @Transactional
    Optional<SectionWithoutCategoryListReadDto> update(Long sectionId, SectionCreateEditDto sectionCreateEditDto);

    @Transactional
    boolean delete(Long id);
}
