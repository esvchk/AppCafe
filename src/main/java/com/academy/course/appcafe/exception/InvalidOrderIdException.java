package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidOrderIdException extends AppCafeException {
    public InvalidOrderIdException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
