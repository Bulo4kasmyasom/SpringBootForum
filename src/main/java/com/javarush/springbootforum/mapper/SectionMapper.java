package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.dto.SectionReadDto;
import com.javarush.springbootforum.dto.SectionWithoutCategoryListReadDto;
import com.javarush.springbootforum.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface SectionMapper {
    SectionReadDto toDto(Section section);

    SectionWithoutCategoryListReadDto toDto2(Section section);

    Section toEntity(SectionCreateEditDto sectionCreateEditDto);

    Section toEntity(@MappingTarget Section section, SectionCreateEditDto sectionCreateEditDto);
}