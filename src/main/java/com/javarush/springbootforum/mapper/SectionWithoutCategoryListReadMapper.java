package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.SectionWithoutCategoryListReadDto;
import com.javarush.springbootforum.entity.Section;
import org.springframework.stereotype.Component;

@Component
public class SectionWithoutCategoryListReadMapper implements Mapper<Section, SectionWithoutCategoryListReadDto> {

    @Override
    public SectionWithoutCategoryListReadDto map(Section object) {
        return new SectionWithoutCategoryListReadDto(
                object.getTitle(),
                object.getDescription()
        );
    }

}
