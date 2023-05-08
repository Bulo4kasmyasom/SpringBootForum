package com.javarush.springbootforum.mapper;


import com.javarush.springbootforum.entity.Section;
import com.javarush.springbootforum.repository.SectionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Mapper
public class CategoryMapperBase {
    private SectionRepository sectionRepository;

    @Autowired
    public void setSectionRepository(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Named("findSectionById")
    protected Section findSectionById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(sectionRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}