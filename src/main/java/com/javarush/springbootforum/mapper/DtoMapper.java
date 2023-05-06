package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.*;
import com.javarush.springbootforum.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DtoMapper {
    DtoMapper MAPPER = Mappers.getMapper(DtoMapper.class);

    UserReadDto userToUserReadDto(User user);

    @Mapping(target = "subCategory.category", source = "subCategory.category.title")
    TopicReadDto topicToTopicReadDto(Topic topic);

    TopicFieldReadDto topicToTopicFieldReadDto(Topic topic);

    @Mapping(target = "subCategory", source = "subCategoryList")
    CategoryReadDto categoryToCategoryReadDto(Category category);

    CategoryFieldReadDto categoryToCategoryFieldReadDto(Category category);

    SectionReadDto sectionToSectionReadDto(Section section);

    SectionWithoutCategoryListReadDto sectionToSectionWithoutCategoryListReadDto(Section section);

    @Mapping(target = "category", source = "subCategory.category.title")
    @Mapping(target = "categoryId", source = "subCategory.category.id")
    SubCategoryReadDto subCategoryToSubCategoryReadDto(SubCategory subCategory);

    SubCategoryFieldReadDto subCategoryToSubCategoryFieldReadDto(SubCategory subCategory);

    TopicMessageReadDto topicMessageToTopicMessageReadDto(TopicMessage topicMessage);


//
//    User userCreateEditDtoToUser(UserCreateEditDto userCreateEditDto); // todo не преобразовывает пароль при регистрации в bcrypt
//
//    @Mapping(target = "section", source = "sectionId")
//    Category categoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto); // todo sectionId in section
//
//    Category categoryEditDtoToCategory(CategoryEditDto categoryEditDto);


}