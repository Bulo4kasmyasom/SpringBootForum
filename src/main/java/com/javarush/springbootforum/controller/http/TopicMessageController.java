package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.service.TopicMessageService;
import com.javarush.springbootforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/topic-message")
@RequiredArgsConstructor
public class TopicMessageController {
    private final TopicMessageService topicMessageService;
    private final UserService userService;

    @PostMapping("/new")
    public String create(@ModelAttribute @Validated TopicMessageCreateEditDto message,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal UserDetails userDetails,
                         @RequestHeader(value = "referer") String referer
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicMessage", message);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors()); // todo выводит ошибки при парсинге Long
            return "redirect:" + referer;
        }

        // только для проверки присланного id и реального id пользователя
        UserReadDto userReadDto = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        topicMessageService.create(userReadDto, message);
        return "redirect:" + referer;
    }

}