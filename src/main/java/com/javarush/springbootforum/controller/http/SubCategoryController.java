package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.dto.SubCategoryCreateDto;
import com.javarush.springbootforum.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.HTTP_SUBCATEGORY_CREATE;
import static com.javarush.springbootforum.controller.constant.MappingPathKey.HTTP_SUBCATEGORY_PATH;

@Controller
@RequestMapping(HTTP_SUBCATEGORY_PATH)
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @PostMapping(HTTP_SUBCATEGORY_CREATE)
    public String create(@ModelAttribute @Validated SubCategoryCreateDto subCategoryCreateDto) {
        subCategoryService.create(subCategoryCreateDto);
        return "redirect:/home";
    }

}