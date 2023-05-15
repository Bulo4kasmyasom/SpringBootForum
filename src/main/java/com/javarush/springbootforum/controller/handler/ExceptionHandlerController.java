package com.javarush.springbootforum.controller.handler;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice(basePackages = "com.javarush.springbootforum.controller.http")
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String handleException() {
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errors", e.getMessage());
        return "redirect:/home";
    }

// todo не работает перехват исключений ниже, поэтому ловим в контроллерах bindingResult
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
//                                                        HttpServletRequest request,
//                                                        RedirectAttributes redirectAttributes) {
//        String errors = e.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(FieldError::getDefaultMessage)
//                .collect(Collectors.joining(", "));
//
//        redirectAttributes.addFlashAttribute("errors", errors);
//
//        String referer = request.getHeader("Referer");
//        return "redirect:" + ((referer != null) ? referer : "/home");
//    }

}
