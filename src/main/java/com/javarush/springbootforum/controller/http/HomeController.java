package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SectionService sectionService;

    @GetMapping(value = {"/home", "/", ""})
    public String showHomePage(Model model) {
        model.addAttribute("sections", sectionService.findAll());
        return "home";
    }
}