package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class EmployeeValidationException extends RuntimeException {

    private final BindingResult bindingResult;

    public EmployeeValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }


}
