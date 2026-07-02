package com.academy.course.appcafe.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class EntityNotFoundByIdException extends AppCafeException {

    public EntityNotFoundByIdException(String message, HttpStatus status, Long entityId) {
        super(message, status);
        this.entityId = entityId;
    }

    public EntityNotFoundByIdException(Long entityId) {
        super("NotFound " + entityId,HttpStatus.NOT_FOUND);
        this.entityId = entityId;
    }

    private final Long entityId;

}
