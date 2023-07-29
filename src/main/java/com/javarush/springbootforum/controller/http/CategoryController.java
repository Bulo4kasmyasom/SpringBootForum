package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.*;
import com.javarush.springbootforum.service.CategoryService;
import com.javarush.springbootforum.service.SubCategoryService;
import com.javarush.springbootforum.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.*;

@Controller
@RequestMapping(HTTP_CATEGORY_PATH)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final TopicService topicService;

    @GetMapping(HTTP_CATEGORIES_WITH_TOPICS)
    public String categoriesWithTopics(@PathVariable("id") Long id, Model model, Pageable pageable) {
        CategoryReadDto categoryDto = categoryService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<SubCategoryReadDto> subCategoryListDto = categoryDto.getSubCategory();

        Page<TopicReadDto> topicsByCategoryId = topicService.findAllByCategoryIdAndSubCategoryIsNull(id, pageable);

        model.addAttribute("pageTitle", categoryDto.getTitle());
        model.addAttribute("category", categoryDto);
        model.addAttribute("subCategories", subCategoryListDto);
        model.addAttribute("topics", PageResponseDto.of(topicsByCategoryId));
        return "categories";
    }

    @GetMapping(HTTP_CATEGORIES_WITH_TOPICS_AND_SUB_CATS)
    public String categoriesWithTopicsAndSubCats(
            @PathVariable("catId") Long catId,
            @PathVariable("subCatId") Long subCatId,
            Model model,
            Pageable pageable
    ) {
        CategoryReadDto categoryDto = categoryService.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        SubCategoryReadDto subCategoryDto = subCategoryService.findById(subCatId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));

        Page<TopicReadDto> topics = topicService.findAllByCategoryIdAndSubCategoryId(catId, subCatId, pageable);

        model.addAttribute("pageTitle", subCategoryDto.getTitle());
        model.addAttribute("category", categoryDto);
        model.addAttribute("subCategory", subCategoryDto);
        model.addAttribute("topics", PageResponseDto.of(topics));
        return "categories";
    }


    @PostMapping(HTTP_CATEGORY_CREATE)
    public String create(@ModelAttribute @Validated CategoryCreateDto categoryCreateDto) {
        categoryService.create(categoryCreateDto);
        return "redirect:/home";
    }

}