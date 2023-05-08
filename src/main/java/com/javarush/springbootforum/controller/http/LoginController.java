package com.javarush.springbootforum.controller.http;


import com.javarush.springbootforum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LoginController {
//    private final CategoryService categoryService;

//    @GetMapping("/login")
//    private String loginPage(Model model) {
//
//        return mainPageService.findPage("login")
//                .map(page -> {
//                    List<CategoryReadDto> category = categoryService.findAll();
//                    model.addAttribute("category", category);
//                    model.addAttribute("main_page", page);
//
//                    return "login";
//                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

}