package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public class EmployeeValidationException extends AppCafeException {

    private final BindingResult bindingResult;

    public EmployeeValidationException(BindingResult bindingResult) {
        super("Validation failed",HttpStatus.BAD_REQUEST);
        this.bindingResult = bindingResult;
    }

    public EmployeeValidationException(String message, HttpStatus status, BindingResult bindingResult) {
        super(message, status);
        this.bindingResult = bindingResult;
    }
}
