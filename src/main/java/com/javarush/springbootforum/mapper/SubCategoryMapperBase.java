package com.javarush.springbootforum.mapper;


import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.repository.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Mapper
public class SubCategoryMapperBase {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setSectionRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Named("findCategoryById")
    protected Category findCategoryById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(categoryRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}