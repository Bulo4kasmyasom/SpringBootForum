package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping("/new")
    public String create(@ModelAttribute @Validated SectionCreateEditDto sectionCreateEditDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/home";
        }
        sectionService.create(sectionCreateEditDto);
        return "redirect:/home";
    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("sections", sectionService.findAll());
        return "home";
    }

}