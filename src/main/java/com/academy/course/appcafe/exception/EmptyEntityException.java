package com.academy.course.appcafe.exception;

import lombok.Getter;

@Getter
public class EmptyEntityException extends ValidationException {

    public EmptyEntityException(Object entity) {
        super("Entity " + entity + " cannot be null or empty");
        this.entity = entity;
    }


    private final Object entity;


}
