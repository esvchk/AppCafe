package com.academy.course.appcafe.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public abstract class AppCafeException extends RuntimeException {

    protected final String message;
    protected final HttpStatus status;

    protected AppCafeException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
