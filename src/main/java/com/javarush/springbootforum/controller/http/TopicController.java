package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.dto.*;
import com.javarush.springbootforum.service.TopicMessageService;
import com.javarush.springbootforum.service.TopicService;
import com.javarush.springbootforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
    private final UserService userService;
    private final TopicService topicService;
    private final TopicMessageService topicMessageService;

    @GetMapping("/{id}")
    public String topicWithMessages(@PathVariable("id") Long id, Model model, Pageable pageable) {

        TopicReadDto topicReadDto = topicService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Page<TopicMessageReadDto> pageMessages = topicMessageService.findAllByTopicId(id, pageable);

        model.addAttribute("topic", topicReadDto);
        model.addAttribute("topicMessages", PageResponseDto.of(pageMessages));
        return "topic";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute @Validated TopicCreateDto topicCreateDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal UserDetails userDetails,
                         @RequestHeader(value = "referer") String referer) {

        UserReadDto userReadDto = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicMessage", topicCreateDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:" + referer;
        }

        TopicReadDto topicReadDto = topicService.create(userReadDto, topicCreateDto);

        return "redirect:/topic/" + topicReadDto.getId();
    }

}