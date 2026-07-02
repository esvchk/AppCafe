package com.academy.course.appcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleEmployeeAlreadyExists(EmployeeAlreadyExists ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponse.builder()
                        .errorCode(ex.getMessage())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(EntityNotFoundByIdException.class)
    @ResponseStatus
    public ResponseEntity<ErrorResponse> handleEntityNotFoundById(EntityNotFoundByIdException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponse.builder()
                        .errorCode(ex.getMessage())
                        .message(ex.getMessage())
                        .build());
    }

}
