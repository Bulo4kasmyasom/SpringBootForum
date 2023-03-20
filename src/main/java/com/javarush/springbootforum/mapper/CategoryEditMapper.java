package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryEditMapper implements Mapper<CategoryEditDto, Category> {

    @Override
    public Category map(CategoryEditDto object) {
        return null;
    }

    @Override
    public Category map(CategoryEditDto fromObject, Category toObject) {
        toObject.setTitle(fromObject.getTitle());
        toObject.setDescription(fromObject.getDescription());
        return toObject;
    }

}
