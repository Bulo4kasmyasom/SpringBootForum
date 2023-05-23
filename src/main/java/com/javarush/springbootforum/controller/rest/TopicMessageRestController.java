package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.service.TopicMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic-message")
@RequiredArgsConstructor
@Tag(name = "TopicMessage Rest Controller", description = "TopicMessage API")
public class TopicMessageRestController {
    private final TopicMessageService topicMessageService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Topic message find by id and return TopicMessageReadDto")
    public TopicMessageReadDto findById(@PathVariable("id") Long id) {
        return topicMessageService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic message not found"));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update topic message and return TopicMessageReadDto")
    public TopicMessageReadDto update(@PathVariable("id") Long topicMessageId,
                                      @RequestBody @Validated TopicMessageCreateEditDto topicMessageCreateEditDto
    ) {
        return topicMessageService.update(topicMessageId, topicMessageCreateEditDto)
                .orElseThrow(() -> new ResourceNotFoundException("Topic message not found"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete topic message by id")
    public HttpStatus delete(@PathVariable("id") Long id) {
        return topicMessageService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;
    }

}