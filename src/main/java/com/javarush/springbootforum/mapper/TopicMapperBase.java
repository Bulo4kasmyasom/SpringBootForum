package com.javarush.springbootforum.mapper;


import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.entity.SubCategory;
import com.javarush.springbootforum.entity.User;
import com.javarush.springbootforum.repository.CategoryRepository;
import com.javarush.springbootforum.repository.SubCategoryRepository;
import com.javarush.springbootforum.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Mapper
public class TopicMapperBase {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private SubCategoryRepository subCategoryRepository;


    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setSubCategoryRepository(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Named("findUserByIdForTopicAuthor")
    protected User findUserByIdForTopicAuthor(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Named("findUserByIdForTopicMessageAuthor")
    protected User findUserByIdForTopicMessageAuthor(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Named("findCategoryById")
    protected Category findCategoryById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(categoryRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Named("findSubCategoryById")
    protected SubCategory findSubCategoryById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(subCategoryRepository::findById)
                .orElse(null);
    }

}