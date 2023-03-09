package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryReadMapper implements Mapper<Category, CategoryReadDto> {

    private final SubCategoryMapper subCategoryMapper;

    @Override
    public CategoryReadDto map(Category object) {
        List<SubCategoryReadDto> subCategoryList = object.getSubCategoryList()
                .stream()
                .map(subCategoryMapper::map)
                .toList();

        return new CategoryReadDto(
                object.getId(),
                object.getTitle(),
                object.getDescription(),
                subCategoryList
        );
    }

}
