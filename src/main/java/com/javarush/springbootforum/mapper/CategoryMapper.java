package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.CategoryCreateDto;
import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {SubCategoryMapper.class, BaseDtoMapper.class})
public interface CategoryMapper {
    @Mapping(target = "subCategory", source = "subCategoryList")
    CategoryReadDto toDto(Category category);

    CategoryFieldReadDto toDto2(Category category);

    @Mapping(target = "section", source = "sectionId")
    Category toEntity(CategoryCreateDto categoryCreateDto);

    Category toEntity(@MappingTarget Category category, CategoryEditDto categoryEditDto);

}
