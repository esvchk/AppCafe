package com.academy.course.appcafe.exception;

public class PasswordValidationException extends ValidationException {
    public PasswordValidationException(String message) {
        super("Password validation error " + message);
    }
}
