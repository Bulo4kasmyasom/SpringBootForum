package com.javarush.springbootforum.controller.handler;

import com.javarush.springbootforum.controller.handler.exception.AccessDeniedException;
import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice(basePackages = "com.javarush.springbootforum.controller.http")
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        e.printStackTrace();
        return "redirect:/home";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException e,
                                              HttpServletRequest request,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errors", e.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + ((referer != null) ? referer : "/home");
    }

    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    public String handleAccessDeniedException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errors", "Access denied");
        return "redirect:/home";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errors", e.getMessage());
        return "redirect:/home";
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class}) // validation in PathVariable
    public String handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException e,
                                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errors", "Ошибочка вышла: Argument Not Valid");
        return "redirect:/home";
    }

    @ExceptionHandler(BindException.class) // validation in DTO fields
    public String handleBindExceptionException(BindException e,
                                               HttpServletRequest request,
                                               RedirectAttributes redirectAttributes) {
        String errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        redirectAttributes.addFlashAttribute("errors", errors);

        String referer = request.getHeader("Referer");
        return "redirect:" + ((referer != null) ? referer : "/home");
    }

}
