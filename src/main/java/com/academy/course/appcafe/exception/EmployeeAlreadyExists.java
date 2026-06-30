package com.academy.course.appcafe.exception;

public class EmployeeAlreadyExists extends BusinessException {
    public EmployeeAlreadyExists(String login) {
        super("Employee with login " + login + " already registered");
        this.login = login;
    }

    private final String login;

}
