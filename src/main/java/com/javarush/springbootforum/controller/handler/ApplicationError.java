package com.javarush.springbootforum.controller.handler;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
public class ApplicationError {
    private int statusCode;
    private String message;
    private Map<String, String> errors;

    public ApplicationError(String message) {
        this.message = message;
    }
}
