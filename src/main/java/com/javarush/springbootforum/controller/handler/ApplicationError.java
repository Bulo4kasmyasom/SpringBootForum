package com.javarush.springbootforum.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

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
