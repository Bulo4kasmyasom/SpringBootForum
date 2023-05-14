package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ValidationException;
import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.dto.SectionWithoutCategoryListReadDto;
import com.javarush.springbootforum.service.SectionService;
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
@RequestMapping("/api/sections")
@RequiredArgsConstructor
@Tag(name = "Section Rest Controller", description = "Section API")
public class SectionRestController {

    private final SectionService sectionService;

    @PutMapping("/{id}")
    @Operation(summary = "Update section and return SectionWithoutCategoryListReadDto")
    public SectionWithoutCategoryListReadDto update(@PathVariable("id") Long sectionId,
                                                    @RequestBody @Validated SectionCreateEditDto sectionCreateEditDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errors);
        }
        return sectionService.update(sectionId, sectionCreateEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // todo возможно нужно изменить возвращаемый тип данных.
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete section by id")
    public HttpStatus delete(@PathVariable("id") Long id) {
        return sectionService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;
    }

}