package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.TopicMessageCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.service.TopicMessageService;
import com.javarush.springbootforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.TOPIC_MESSAGE_CREATE;
import static com.javarush.springbootforum.controller.constant.MappingPathKey.TOPIC_MESSAGE_PATH;

@Controller
@RequestMapping(TOPIC_MESSAGE_PATH)
@RequiredArgsConstructor
public class TopicMessageController {

    private final TopicMessageService topicMessageService;
    private final UserService userService;

    @PostMapping(TOPIC_MESSAGE_CREATE)
    public String create(@ModelAttribute @Validated TopicMessageCreateEditDto message,
                         @AuthenticationPrincipal UserDetails userDetails,
                         @RequestHeader(value = "referer") String referer
    ) {
        UserReadDto userReadDto = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        topicMessageService.create(userReadDto.getId(), message);
        return "redirect:" + referer;
    }

}