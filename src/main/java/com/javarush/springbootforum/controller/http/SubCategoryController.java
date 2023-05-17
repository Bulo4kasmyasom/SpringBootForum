package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.dto.SubCategoryCreateDto;
import com.javarush.springbootforum.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subcat")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @PostMapping("/new")
    public String create(@ModelAttribute @Validated SubCategoryCreateDto subCategoryCreateDto) {
        subCategoryService.create(subCategoryCreateDto);
        return "redirect:/home";
    }

}