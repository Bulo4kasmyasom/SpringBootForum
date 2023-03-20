package com.javarush.springbootforum.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.javarush.springbootforum.controller.http")
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        exception.printStackTrace(); // писать в логи нужно
        return "redirect:/home";
    }

}
