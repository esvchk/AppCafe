package com.academy.course.appcafe.exception;

import lombok.Getter;

@Getter
public class ProductAddingException extends RuntimeException {

    public ProductAddingException(String message) {
        super(message);
    }

}
