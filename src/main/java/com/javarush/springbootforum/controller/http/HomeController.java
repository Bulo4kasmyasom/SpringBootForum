package com.javarush.springbootforum.controller.http;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping(value = {HOME_PATH, HOME_PATH_SLASH, HOME_PATH_EMPTY})
    public String showHomePage() {
        return "forward:/sections";
    }
}