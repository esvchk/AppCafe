package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PaginationException extends AppCafeException {

    private final String fallbackUrl;

    public PaginationException(String message, String fallbackUrl) {
        super(message, HttpStatus.BAD_REQUEST);
        this.fallbackUrl = fallbackUrl;
    }


}
