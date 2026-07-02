package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmptyEntityException extends ValidationException {

    public EmptyEntityException(Object entity) {
        super("Entity " + entity + " cannot be null or empty", HttpStatus.BAD_GATEWAY);
        this.entity = entity;
    }


    private final Object entity;


}
