package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmptyFieldException extends ValidationException {
    private final String value;

    public EmptyFieldException(String value) {
        super("Value " + value + " cannot be empty or null", HttpStatus.BAD_GATEWAY);
        this.value = value;
    }

    public EmptyFieldException(String message, String value) {
        super(message,HttpStatus.BAD_GATEWAY);
        this.value = value;
    }
}
