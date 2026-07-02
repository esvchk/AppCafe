package com.academy.course.appcafe.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidInputException extends ValidationException {

    private final String input;

    public InvalidInputException(String input) {
        super("Wrong input " + input, HttpStatus.BAD_GATEWAY);
        this.input = input;
    }

    public InvalidInputException(String message, String input) {
        super(message,HttpStatus.BAD_GATEWAY);
        this.input = input;
    }
}
