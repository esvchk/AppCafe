package com.academy.course.appcafe.exception;

public class WrongLoginException extends BusinessException {
    public WrongLoginException(String message) {
        super(message);
    }
}
