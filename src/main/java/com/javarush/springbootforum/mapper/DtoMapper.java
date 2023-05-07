package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.*;
import com.javarush.springbootforum.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {BaseDtoMapper.class})
public interface DtoMapper {
    @Mapping(target="lastActivity", dateFormat = " dd.MM.yyyy hh:mm:ss")
    UserReadDto userToUserReadDto(User user);

    @Mapping(target = "password", expression = "java(baseDtoMapper.setEncodingPasswordToUser(userCreateEditDto))")
    @Mapping(target = "role", expression = "java(baseDtoMapper.setDefaultRoleToUser(userCreateEditDto))")
    User userCreateEditDtoToUser(UserCreateEditDto userCreateEditDto);

    @Mapping(target = "subCategory.category", source = "subCategory.category.title")
    @Mapping(target="createdAt", dateFormat = "dd.MM.yyyy hh:mm:ss")
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

    @Mapping(target = "section", source = "sectionId")
    Category categoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto);

    @Mapping(target = "category", source = "categoryId")
    SubCategory subCategoryCreateDtoToSubCategory(SubCategoryCreateDto subCategoryCreateDto);

    @Mapping(target = "topic.title", source = "topicTitle")
    @Mapping(target = "topic.category", source = "categoryId")
    @Mapping(target = "topic.subCategory", source = "subCategoryId")
    @Mapping(target = "topic.author", source = "authorId")
    @Mapping(target = "author", source = "authorId")
    @Mapping(target = "text", source = "messageText")
    TopicMessage topicCreateDtoToTopicMessage(TopicCreateDto topic);

    @Mapping(target = "topic", source = "topicId")
    @Mapping(target = "author", source = "authorId")
    TopicMessage topicMessageCreateEditDtoToTopicMessage(TopicMessageCreateEditDto topicMessageCreateEditDto);

    TopicMessage updateFromTopicMessageCreateEditDtoToTopicMessage(@MappingTarget TopicMessage topicMessage, TopicMessageCreateEditDto topicMessageCreateEditDto);

    Section updateFromSectionDtoToSection(SectionCreateEditDto sectionCreateEditDto);

    @Mapping(target = "password", expression = "java(baseDtoMapper.setEncodingPasswordToUser(userCreateEditDto))")
    User updateFromUserCreateEditDtoToUser(@MappingTarget User user, UserCreateEditDto userCreateEditDto);

    Section updateFromSectionDtoToSection(@MappingTarget Section section, SectionCreateEditDto sectionCreateEditDto);

    Category updateFromCategoryDtoToCategory(@MappingTarget Category category, CategoryEditDto categoryEditDto);

    SubCategory updateFromSubCategoryDtoToSubCategory(@MappingTarget SubCategory subCategory, SubCategoryEditDto subCategoryEditDto);

    Topic updateFromTopicDtoToTopic(@MappingTarget Topic topic, TopicEditDto topicEditDto);

}