package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.dto.SectionWithoutCategoryListReadDto;
import com.javarush.springbootforum.service.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.*;

@RestController
@RequestMapping(API_PATH + REST_SECTION_PATH)
@RequiredArgsConstructor
@Tag(name = "Section Rest Controller", description = "Section API")
public class SectionRestController {

    private final SectionService sectionService;

    @PutMapping(REST_SECTION_UPDATE)
    @Operation(summary = "Update section and return SectionWithoutCategoryListReadDto")
    public SectionWithoutCategoryListReadDto update(@PathVariable("id") Long sectionId,
                                                    @RequestBody @Validated SectionCreateEditDto sectionCreateEditDto) {
        return sectionService.update(sectionId, sectionCreateEditDto)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
    }

    @DeleteMapping(REST_SECTION_DELETE)
    @Operation(summary = "Delete section by id")
    public HttpStatus delete(@PathVariable("id") Long id) {
        return sectionService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;
    }

}