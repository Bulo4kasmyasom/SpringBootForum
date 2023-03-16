package com.javarush.springbootforum.controller.handler;

import lombok.Value;

@Value
public class ApplicationError {
    int statusCode;
    String message;
}
