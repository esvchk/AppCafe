package com.academy.course.appcafe.exception;


import lombok.Getter;

@Getter
public class InvalidInputException extends ValidationException {

    private final String input;

    public InvalidInputException(String input) {
        super("Wrong input " + input);
        this.input = input;
    }

    public InvalidInputException(String message, String input) {
        super(message);
        this.input = input;
    }
}
