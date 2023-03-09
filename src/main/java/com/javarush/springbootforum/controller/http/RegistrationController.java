package com.javarush.springbootforum.controller.http;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
//    private final MainPageService mainPageService;
//
//    @GetMapping("/registration")
//    public String registrationPage(Model model, @ModelAttribute("user") UserCreateDto user) {
//        return mainPageService.findPage("registration")
//                .map(page -> {
//                    model.addAttribute("main_page", page);
//                    model.addAttribute("user", user); // для запоминания того, что было введено если не валид
//                    return "registration";
//                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
}