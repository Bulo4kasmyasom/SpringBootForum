package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.*;
import com.javarush.springbootforum.service.TopicMessageService;
import com.javarush.springbootforum.service.TopicService;
import com.javarush.springbootforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.*;

@Controller
@RequestMapping(HTTP_TOPIC_PATH)
@RequiredArgsConstructor
public class TopicController {

    private final UserService userService;
    private final TopicService topicService;
    private final TopicMessageService topicMessageService;

    @GetMapping(HTTP_TOPIC_WITH_MESSAGES)
    public String topicWithMessages(@PathVariable("id") Long id, Model model, Pageable pageable) {
        TopicReadDto topicReadDto = topicService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        Page<TopicMessageReadDto> pageMessages = topicMessageService.findAllByTopicId(id, pageable);

        model.addAttribute("pageTitle", topicReadDto.getTitle());
        model.addAttribute("topic", topicReadDto);
        model.addAttribute("topicMessages", PageResponseDto.of(pageMessages));
        return "topic";
    }


    @PostMapping(HTTP_TOPIC_CREATE)
    public String create(@ModelAttribute @Validated TopicCreateDto topicCreateDto,
                         @AuthenticationPrincipal UserDetails userDetails) {
        UserReadDto userReadDto = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TopicReadDto topicReadDto = topicService.create(userReadDto, topicCreateDto);
        return "redirect:/topic/" + topicReadDto.getId();
    }

}