package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundByNameException extends AppCafeException {


    public EntityNotFoundByNameException(String name) {
        super("Entity with name " + name + " not found", HttpStatus.NOT_FOUND);
        this.name = name;
    }

    private final String name;
}
