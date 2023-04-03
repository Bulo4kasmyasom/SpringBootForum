package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.dto.SectionReadDto;
import com.javarush.springbootforum.dto.SectionWithoutCategoryListReadDto;
import com.javarush.springbootforum.mapper.SectionCreateEditMapper;
import com.javarush.springbootforum.mapper.SectionReadMapper;
import com.javarush.springbootforum.mapper.SectionWithoutCategoryListReadMapper;
import com.javarush.springbootforum.repository.SectionRepository;
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
public class SectionService {
    private final SectionRepository sectionRepository;
    private final SectionReadMapper sectionReadMapper;
    private final SectionCreateEditMapper sectionCreateEditMapper;
    private final SectionWithoutCategoryListReadMapper sectionWithoutCategoryListReadMapper;

    public List<SectionReadDto> findAll() {
        return sectionRepository.findAll()
                .stream()
                .map(sectionReadMapper::map)
                .toList();
    }

    public Optional<SectionReadDto> findById(Long id) {
        return sectionRepository.findById(id)
                .map(sectionReadMapper::map);
    }

    @Transactional
    public SectionReadDto create(SectionCreateEditDto sectionCreateEditDto) {
        return Optional.of(sectionCreateEditDto)
                .map(sectionCreateEditMapper::map)
                .map(sectionRepository::save)
                .map(sectionReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Optional<SectionWithoutCategoryListReadDto> update(Long sectionId, SectionCreateEditDto sectionCreateEditDto) {
        return sectionRepository.findById(sectionId)
                .map(section -> sectionCreateEditMapper.map(sectionCreateEditDto, section))
                .map(sectionRepository::saveAndFlush)
                .map(sectionWithoutCategoryListReadMapper::map);
    }

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