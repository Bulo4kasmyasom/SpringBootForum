package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.dto.PageResponseDto;
import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.dto.TopicReadDto;
import com.javarush.springbootforum.service.CategoryService;
import com.javarush.springbootforum.service.SubCategoryService;
import com.javarush.springbootforum.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/cat")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final TopicService topicService;

    @GetMapping("/{id}")
    public String categoriesWithTopics(@PathVariable("id") Long id, Model model, Pageable pageable) {
        CategoryReadDto categoryDto = categoryService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<SubCategoryReadDto> subCategoryListDto = categoryDto.getSubCategory();

        Page<TopicReadDto> topicsByCategoryId = topicService.findAllByCategoryIdAndSubCategoryIsNull(id, pageable);

        model.addAttribute("category", categoryDto);
        model.addAttribute("subCategories", subCategoryListDto);
        model.addAttribute("topics", PageResponseDto.of(topicsByCategoryId));
        return "categories";
    }

    @GetMapping("/{catId}/subcat/{subCatId}")
    public String categoriesWithTopicsAndSubCats(
            @PathVariable("catId") Long catId,
            @PathVariable("subCatId") Long subCatId,
            Model model,
            Pageable pageable
    ) {
        CategoryReadDto categoryDto = categoryService.findById(catId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SubCategoryReadDto subCategoryDto = subCategoryService.findById(subCatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Page<TopicReadDto> topicsByCategoryAndSubCategory = topicService.findAllByCategoryIdAndSubCategoryId(catId, subCatId, pageable);

        model.addAttribute("category", categoryDto);
        model.addAttribute("subCategory", subCategoryDto);
        model.addAttribute("topics", PageResponseDto.of(topicsByCategoryAndSubCategory));
        return "categories";
    }

}