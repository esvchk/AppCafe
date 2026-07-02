package com.academy.course.appcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class AuthorizationException extends AppCafeException{

    public AuthorizationException(String message) {
        super("Authorization failed" + message,HttpStatus.FORBIDDEN);
    }



}
