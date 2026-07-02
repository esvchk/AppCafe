package com.academy.course.appcafe.exception;

import org.springframework.http.HttpStatus;

public class PasswordValidationException extends ValidationException {
    public PasswordValidationException(String message) {
        super("Password validation error " + message, HttpStatus.BAD_GATEWAY);
    }
}
