package com.academy.course.appcafe.exception;

public class EntityNotFoundByIdException extends BusinessException {
    public EntityNotFoundByIdException(String message, Long entityId) {
        super(message);
        this.entityId = entityId;
    }

    public EntityNotFoundByIdException(Long entityId) {
        super("Entity with id " + entityId + " not found");
        this.entityId = entityId;
    }

    public EntityNotFoundByIdException(Throwable cause, Long entityId) {
        super("Entity with id " + entityId + " not found", cause);
        this.entityId = entityId;
    }

    private final Long entityId;

}
