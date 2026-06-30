package com.academy.course.appcafe.exception;

import lombok.Getter;

@Getter
public class OrderPurchasingException extends RuntimeException {
    public OrderPurchasingException(Integer orderId) {
        super("Order with id " + orderId + " already has been bought");
        this.orderId = orderId;
    }

    private final Integer orderId;

}
