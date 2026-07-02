package com.academy.course.appcafe.exception;

import org.springframework.http.HttpStatus;

public class WrongValueException extends ValidationException {

    private final String value;

    public WrongValueException(String message,String value) {
        super(message,HttpStatus.BAD_GATEWAY);
        this.value = value;
    }
}
