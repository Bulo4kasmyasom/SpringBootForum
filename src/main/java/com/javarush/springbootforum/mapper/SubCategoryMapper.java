package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.SubCategoryCreateDto;
import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {SubCategoryMapperBase.class})
public interface SubCategoryMapper {

    @Mapping(target = "category", source = "subCategory.category.title")
    @Mapping(target = "categoryId", source = "subCategory.category.id")
    SubCategoryReadDto toDto(SubCategory subCategory);

    SubCategoryFieldReadDto toDto2(SubCategory subCategory);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "findCategoryById")
    SubCategory toEntity(SubCategoryCreateDto subCategoryCreateDto);

    SubCategory toEntity(@MappingTarget SubCategory subCategory, SubCategoryEditDto subCategoryEditDto);
}