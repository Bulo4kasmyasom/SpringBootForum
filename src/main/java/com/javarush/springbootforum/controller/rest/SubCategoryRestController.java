package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ValidationException;
import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subcat")
@RequiredArgsConstructor
public class SubCategoryRestController {

    private final SubCategoryService subCategoryService;

    @PutMapping("/{id}")
    public SubCategoryFieldReadDto update(@PathVariable("id") Long id,
                                          @RequestBody @Validated SubCategoryEditDto subCategoryEditDto,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errors);
        }
        return subCategoryService.update(id, subCategoryEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}