package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.entity.*;
import com.javarush.springbootforum.repository.*;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Mapper(componentModel = "spring")
public class BaseDtoMapper {
    // todo убрать все методы по своим мапперам. Но сперва разобраться с тем,
    //  как инжектить репозитории в разные мапперы. Поэтому используется абстрактный класс.

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
    PasswordEncoder passwordEncoder;
    protected Role role;

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
