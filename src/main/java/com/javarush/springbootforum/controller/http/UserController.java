package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.PageResponseDto;
import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.service.UserService;
import com.javarush.springbootforum.validation.OnCreatable;
import com.javarush.springbootforum.validation.OnUpdatable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.javarush.springbootforum.controller.constant.MappingPathKey.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(USERS_PATH)
public class UserController {

    private final UserService userService;
    private final List<String> getDefaultAvatarImagesList;

    @GetMapping
    public String findAll(Model model, Pageable pageable, @Value("${page.title.users.findAll}") String pageTitle) {
        Page<UserReadDto> page = userService.findAll(pageable);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("users", PageResponseDto.of(page));
        return "users";
    }

    @GetMapping(USERS_FIND_BY_ID)
    public String findById(@PathVariable("id") Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("pageTitle", user.getUsername());
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("defaultAvatars", getDefaultAvatarImagesList);
                    return "user";
                })
                .orElse("redirect:/users");
    }

    @PostMapping(USERS_REGISTRATION)
    public String create(@ModelAttribute @Validated(OnCreatable.class) UserCreateEditDto user) {
        userService.create(user);
        return "redirect:/home";
    }

    @PostMapping(USERS_UPDATE)
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute @Validated(OnUpdatable.class) UserCreateEditDto user) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @PostMapping(USERS_DELETE)
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResourceNotFoundException("User not found, not deleted");
        }
        return "redirect:/users";
    }

}