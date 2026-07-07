package com.academy.course.appcafe.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class EntityNotFoundByIdException extends AppCafeException {

    public EntityNotFoundByIdException(String message, HttpStatus status, Long entityId) {
        super(message, status);
        this.entityId = entityId;
    }

    public EntityNotFoundByIdException(Long entityId) {
        super("NotFound with id " + entityId,HttpStatus.NOT_FOUND);
        this.entityId = entityId;
    }

    private final Long entityId;

}
