package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.SectionReadDto;
import com.javarush.springbootforum.mapper.SectionReadMapper;
import com.javarush.springbootforum.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectionService {

    private final SectionRepository sectionRepository;
    private final SectionReadMapper sectionReadMapper;

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

}