package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@RequiredArgsConstructor
@Validated
@Tag(name = "Category Rest Controller", description = "Category API")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Find all categories and return list of CategoryReadDto")
    public List<CategoryReadDto> findAll() {
        return categoryService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category and return CategoryFieldReadDto")
    public CategoryFieldReadDto update(@PathVariable("id") Long id,
                                       @RequestBody @Validated CategoryEditDto categoryEditDto) {
        return categoryService.update(id, categoryEditDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id")
    public HttpStatus delete(@PathVariable("id") Long id) {
        return categoryService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;
    }

}