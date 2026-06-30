package com.academy.course.appcafe.exception;

public abstract class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }

}
