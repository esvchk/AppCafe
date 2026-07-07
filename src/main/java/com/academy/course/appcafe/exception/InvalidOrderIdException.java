package com.academy.course.appcafe.exception;

import org.springframework.http.HttpStatus;

public class InvalidOrderIdException extends AppCafeException {
    public InvalidOrderIdException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
