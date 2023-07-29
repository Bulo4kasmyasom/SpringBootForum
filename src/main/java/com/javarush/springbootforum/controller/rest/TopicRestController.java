package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.TopicEditDto;
import com.javarush.springbootforum.dto.TopicFieldReadDto;
import com.javarush.springbootforum.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.*;

@RestController
@RequestMapping(API_PATH + REST_TOPIC_PATH)
@RequiredArgsConstructor
@Tag(name = "Topic Rest Controller", description = "Topic API")
public class TopicRestController {

    private final TopicService topicService;

    @PutMapping(REST_TOPIC_UPDATE)
    @Operation(summary = "Update topic by topicId, and return TopicFieldReadDto")
    public TopicFieldReadDto update(@PathVariable("id") Long topicId,
                                    @RequestBody @Validated TopicEditDto topicEditDto) {
        return topicService.update(topicId, topicEditDto)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
    }

    @PatchMapping(REST_TOPIC_MOVE_TOPICS_IN_CATEGORY)
    @Operation(summary = "Move topics in category")
    public HttpStatus moveTopicsInCategory(@PathVariable("catId") Long catId,
                                           @RequestBody Long[] topicsIds) {
        return topicService.moveTopicsInCategory(catId, topicsIds)
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
    }

    @PatchMapping(REST_TOPIC_MOVE_TOPICS_IN_SUB_CATEGORY)
    @Operation(summary = "Move topics in subcategory")
    public HttpStatus moveTopicsInSubCategory(@PathVariable("catId") Long catId,
                                              @PathVariable(value = "subCatId") Long subCatId,
                                              @RequestBody Long[] topicsIds) {
        return topicService.moveTopicsInSubCategory(catId, subCatId, topicsIds)
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping(REST_TOPIC_DELETE)
    @Operation(summary = "Delete topic by id")
    public HttpStatus delete(@PathVariable("id") Long id) {
        return topicService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;
    }

    @DeleteMapping
    @Operation(summary = "Mass delete topics by ids")
    public HttpStatus massDelete(@RequestBody Long[] topicsIds) {
        return topicService.massDelete(topicsIds)
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
    }

}