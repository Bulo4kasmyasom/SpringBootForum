package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryFieldReadMapper implements Mapper<Category, CategoryFieldReadDto> {

    @Override
    public CategoryFieldReadDto map(Category object) {
        return new CategoryFieldReadDto(
                object.getTitle(),
                object.getDescription()
        );
    }

}
