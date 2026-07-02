package com.academy.course.appcafe.exception;

import org.springframework.http.HttpStatus;

public class LoginValidationException extends ValidationException{
    public LoginValidationException(String message) {
        super("Login validation error " + message, HttpStatus.NOT_ACCEPTABLE);
    }
}
