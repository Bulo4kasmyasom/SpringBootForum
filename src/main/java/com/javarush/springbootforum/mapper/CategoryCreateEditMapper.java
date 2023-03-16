package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.CategoryCreateEditDto;
import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.entity.Section;
import com.javarush.springbootforum.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryCreateEditMapper implements Mapper<CategoryCreateEditDto, Category> {

    private final SectionRepository sectionRepository;

    @Override
    public Category map(CategoryCreateEditDto object) {
        return Category.builder()
                .title(object.getTitle())
                .description(object.getDescription())
                .section(getSection(object.getSectionId()))
                .build();
    }

    private Section getSection(Long sectionId) {
        return Optional.ofNullable(sectionId)
                .flatMap(sectionRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
