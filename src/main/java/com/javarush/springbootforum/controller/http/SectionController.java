package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping("/new")
    public String create(@ModelAttribute @Validated SectionCreateEditDto sectionCreateEditDto) {
        sectionService.create(sectionCreateEditDto);
        return "redirect:/home";
    }


    @GetMapping
    public String findAll(Model model, @Value("${page.title.home}") String pageTitle) {
        model.addAttribute("sections", sectionService.findAll());
        model.addAttribute("pageTitle", pageTitle);
        return "home";
    }

}