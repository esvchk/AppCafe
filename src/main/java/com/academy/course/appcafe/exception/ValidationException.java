package com.academy.course.appcafe.exception;

import org.springframework.http.HttpStatus;

public abstract class ValidationException extends AppCafeException {
    public ValidationException(String message, HttpStatus status) {
        super(message, status);
    }
}
