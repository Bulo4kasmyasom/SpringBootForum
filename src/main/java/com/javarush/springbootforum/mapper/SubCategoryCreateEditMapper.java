package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.SubCategoryCreateEditDto;
import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.entity.SubCategory;
import com.javarush.springbootforum.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubCategoryCreateEditMapper implements Mapper<SubCategoryCreateEditDto, SubCategory> {

    private final CategoryRepository categoryRepository;

    @Override
    public SubCategory map(SubCategoryCreateEditDto object) {
        return SubCategory.builder()
                .title(object.getTitle())
                .description(object.getDescription())
                .category(getCategory(object.getCategoryId()))
                .build();
    }

    private Category getCategory(Long categoryId) {
        return Optional.ofNullable(categoryId)
                .flatMap(categoryRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
