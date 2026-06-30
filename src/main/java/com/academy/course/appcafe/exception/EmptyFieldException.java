package com.academy.course.appcafe.exception;

import lombok.Getter;

@Getter
public class EmptyFieldException extends ValidationException {
    private final String value;

    public EmptyFieldException(String value) {
        super("Value " + value + " cannot be empty or null");
        this.value = value;
    }

    public EmptyFieldException(String message, String value) {
        super(message);
        this.value = value;
    }
}
