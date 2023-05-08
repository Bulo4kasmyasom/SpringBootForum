package com.javarush.springbootforum.service.impl;

import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.dto.SectionReadDto;
import com.javarush.springbootforum.dto.SectionWithoutCategoryListReadDto;
import com.javarush.springbootforum.mapper.SectionMapper;
import com.javarush.springbootforum.repository.SectionRepository;
import com.javarush.springbootforum.service.SectionService;
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
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;

    @Override
    public List<SectionReadDto> findAll() {
        return sectionRepository.findAll()
                .stream()
                .map(sectionMapper::toDto)
                .toList();
    }

    @Override
    public Optional<SectionReadDto> findById(Long id) {
        return sectionRepository.findById(id)
                .map(sectionMapper::toDto);
    }

    @Override
    @Transactional
    public SectionReadDto create(SectionCreateEditDto sectionCreateEditDto) {
        return Optional.of(sectionCreateEditDto)
                .map(sectionMapper::toEntity)
                .map(sectionRepository::save)
                .map(sectionMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Optional<SectionWithoutCategoryListReadDto> update(Long sectionId, SectionCreateEditDto sectionCreateEditDto) {
        return sectionRepository.findById(sectionId)
                .map(section -> sectionMapper.toEntity(section, sectionCreateEditDto))
                .map(sectionRepository::saveAndFlush)
                .map(sectionMapper::toDto2);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return sectionRepository.findById(id)
                .map(topic -> {
                    sectionRepository.delete(topic);
                    sectionRepository.flush();
                    return true;
                }).orElse(false);
    }
}