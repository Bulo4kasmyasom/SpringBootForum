package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.dto.SectionReadDto;
import com.javarush.springbootforum.entity.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SectionReadMapper implements Mapper<Section, SectionReadDto> {

    private final CategoryReadMapper categoryReadMapper;

    @Override
    public SectionReadDto map(Section object) {
        List<CategoryReadDto> subCategoryList = object.getCategoryList()
                .stream()
                .map(categoryReadMapper::map)
                .toList();

        return new SectionReadDto(
                object.getId(),
                object.getTitle(),
                object.getDescription(),
                subCategoryList
        );
    }

}
