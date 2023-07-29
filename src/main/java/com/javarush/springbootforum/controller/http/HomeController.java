package com.javarush.springbootforum.controller.http;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping(value = {HTTP_HOME_PATH, HTTP_HOME_PATH_SLASH, HTTP_HOME_PATH_EMPTY})
    public String showHomePage() {
        return "forward:/sections";
    }
}