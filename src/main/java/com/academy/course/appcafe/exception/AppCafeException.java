package com.academy.course.appcafe.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public abstract class AppCafeException extends RuntimeException {

    protected final String message;
    protected final HttpStatus status;

    protected AppCafeException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
