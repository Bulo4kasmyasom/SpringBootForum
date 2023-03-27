package com.javarush.springbootforum.controller.rest;

import com.javarush.springbootforum.config.SecurityConfiguration;
import com.javarush.springbootforum.controller.handler.exception.ValidationException;
import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.service.TopicMessageService;
import com.javarush.springbootforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topic-message")
@RequiredArgsConstructor
public class TopicMessageRestController {
    private final TopicMessageService topicMessageService;
    private final UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TopicMessageReadDto findById(@PathVariable("id") Long id) {
        return topicMessageService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}")
    public TopicMessageReadDto update(@PathVariable("id") Long topicMessageId,
                                      @RequestBody @Validated TopicMessageCreateEditDto topicMessageCreateEditDto,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errors);
        }

        UserReadDto userReadDto = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        topicMessageCreateEditDto.setAuthorId(userReadDto.getId()); // todo сеттер в контроллере - плохо?

        return topicMessageService.update(topicMessageId, topicMessageCreateEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    // todo возможно нужно изменить возвращаемый тип данных.
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable("id") Long id) {
        return topicMessageService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;
    }

}