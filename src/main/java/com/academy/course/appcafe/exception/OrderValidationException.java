package com.academy.course.appcafe.exception;

import org.springframework.http.HttpStatus;

public class OrderValidationException extends AppCafeException {
    private final Long orderId;

    public OrderValidationException(Long orderId, String message) {
        super(message, HttpStatus.BAD_REQUEST);
        this.orderId = orderId;
    }
    public Long getOrderId() { return orderId; }
}
