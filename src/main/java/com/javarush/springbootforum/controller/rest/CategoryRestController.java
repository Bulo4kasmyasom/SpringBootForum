package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ValidationException;
import com.javarush.springbootforum.dto.CategoryEditDto;
import com.javarush.springbootforum.dto.CategoryFieldReadDto;
import com.javarush.springbootforum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cat")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @PutMapping("/{id}")
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

}