package com.academy.course.appcafe.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundByNameException extends RuntimeException {


    public EntityNotFoundByNameException(String name) {
        super("Entity with name " + name + " not found");
        this.name = name;
    }

    private final String name;
}
