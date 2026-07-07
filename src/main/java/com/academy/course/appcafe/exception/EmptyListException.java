package com.academy.course.appcafe.exception;


import org.springframework.http.HttpStatus;

public class EmptyListException extends AppCafeException {
    public EmptyListException() {
        super("Empty List", HttpStatus.NOT_FOUND);

    }
}
