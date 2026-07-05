package com.academy.course.appcafe.exception;

public class OrderValidationException extends RuntimeException {
    private final Long orderId;

    public OrderValidationException(Long orderId, String message) {
        super(message);
        this.orderId = orderId;
    }
    public Long getOrderId() { return orderId; }
}
