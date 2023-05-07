package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.entity.*;
import com.javarush.springbootforum.repository.*;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {
        SectionRepository.class, CategoryRepository.class,
        SubCategoryRepository.class, UserRepository.class,
        TopicRepository.class, PasswordEncoder.class
})
public class BaseDtoMapper {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    protected Role role;

    protected String setEncodingPasswordToUser(UserCreateEditDto userCreateEditDto) {
        return passwordEncoder.encode(userCreateEditDto.getPassword());
    }

    protected Role setDefaultRoleToUser(UserCreateEditDto userCreateEditDto) {
        return userCreateEditDto.getRole() == null ? Role.USER : Role.valueOf(userCreateEditDto.getRole());
    }

    protected Topic findTopicById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(topicRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    protected Section findSectionById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(sectionRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    protected User findUserById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    protected Category findCategoryById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(categoryRepository::findById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    protected SubCategory findSubCategoryById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(subCategoryRepository::findById)
                .orElse(null);
    }
}
