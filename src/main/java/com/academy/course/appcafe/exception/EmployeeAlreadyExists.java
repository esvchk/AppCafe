package com.academy.course.appcafe.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmployeeAlreadyExists extends AppCafeException {
    public EmployeeAlreadyExists(String login) {
        super("Employee with login " + login + " already registered",HttpStatus.CONFLICT);
        this.login = login;
    }

    public EmployeeAlreadyExists(String message, HttpStatus status, String login) {
        super(message, status);
        this.login = login;
    }

    private final String login;

}
