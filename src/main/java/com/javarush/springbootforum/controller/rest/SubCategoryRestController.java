package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ValidationException;
import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.service.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "SubCategory Rest Controller", description = "SubCategory API")
public class SubCategoryRestController {

    private final SubCategoryService subCategoryService;

    @PutMapping("/{id}")
    @Operation(summary = "Update subcategory and return SubCategoryFieldReadDto")
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

    // todo возможно нужно изменить возвращаемый тип данных.
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete subcategory by id")
    public HttpStatus delete(@PathVariable("id") Long id) {
        return subCategoryService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;
    }

}