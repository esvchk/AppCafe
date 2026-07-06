package com.academy.course.appcafe.exception;

import lombok.Getter;

@Getter
public class PaginationException extends RuntimeException {

    public PaginationException(String message) {
        super(message);
    }

}
