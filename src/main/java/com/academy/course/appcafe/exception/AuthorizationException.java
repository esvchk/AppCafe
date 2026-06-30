package com.academy.course.appcafe.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super("Authorization failed " + message);
    }


}
