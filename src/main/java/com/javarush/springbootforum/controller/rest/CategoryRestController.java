package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ValidationException;
import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.dto.CategoryReadDto;
import com.javarush.springbootforum.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cat")
@RequiredArgsConstructor
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
                                       @RequestBody @Validated CategoryEditDto categoryEditDto,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errors);
        }
        return categoryService.update(id, categoryEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // todo возможно нужно изменить возвращаемый тип данных.
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id")
    public HttpStatus delete(@PathVariable("id") Long id) {
        return categoryService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;
    }

}