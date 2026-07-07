package com.academy.course.appcafe.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmptyListException extends AppCafeException {
    public EmptyListException() {
        super("Empty List", HttpStatus.NOT_FOUND);

    }
}
