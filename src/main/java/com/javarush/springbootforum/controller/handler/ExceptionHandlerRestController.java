package com.javarush.springbootforum.controller.handler;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.controller.handler.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice(basePackages = "com.javarush.springbootforum.controller.rest")
public class ExceptionHandlerRestController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApplicationError handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ApplicationError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ApplicationError handleValidationException(ValidationException e) {
        return new ApplicationError(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError handleIllegalStateException(IllegalStateException e) {
        return new ApplicationError(e.getMessage());
    }

    /*
    ConstraintViolationException возникает в Spring при нарушении ограничений целостности данных в базе данных.
    Это может произойти, например, когда вы пытаетесь сохранить объект в базу данных, который содержит значения,
    не соответствующие определенным ограничениям, таким как уникальность значений или ссылочная целостность.
    В таком случае, Hibernate (или другой ORM) генерирует исключение ConstraintViolationException, которое
    перехватывается Spring и преобразуется в HTTP-ответ с кодом ошибки 400 (Bad Request).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError handleConstraintViolationException(ConstraintViolationException e) {
        ApplicationError ApplicationError = new ApplicationError("Validation failed.");
        ApplicationError.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )));
        return ApplicationError;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class}) // validation in dto
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApplicationError applicationError = new ApplicationError("Validation failed.");
        List<FieldError> errorsList = e.getBindingResult().getFieldErrors();

        String errors = errorsList.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        applicationError.setMessage(errors);

        return applicationError;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationError handleException(Exception e) {
        e.printStackTrace();
        return new ApplicationError("Internal error.");
    }

}
