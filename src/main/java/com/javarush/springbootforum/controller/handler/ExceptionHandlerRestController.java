package com.javarush.springbootforum.controller.handler;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.controller.handler.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ControllerAdvice(basePackages = "com.javarush.springbootforum.controller.rest")
public class ExceptionHandlerRestController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ApplicationError handleException(ResourceNotFoundException e) {
//        exception.printStackTrace(); // писать в логи нужно
        return new ApplicationError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ApplicationError handleException(ValidationException e) {
//        exception.printStackTrace(); // писать в логи нужно
        return new ApplicationError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}
