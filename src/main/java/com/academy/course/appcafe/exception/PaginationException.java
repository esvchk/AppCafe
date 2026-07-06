package com.academy.course.appcafe.exception;

import lombok.Getter;

@Getter
public class PaginationException extends RuntimeException {

    private final String fallbackUrl;

    public PaginationException(String message, String fallbackUrl) {
        super(message);
        this.fallbackUrl = fallbackUrl;
    }


}
