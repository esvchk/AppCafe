package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class QuantityOutOfBoundsException extends AppCafeException {
    public QuantityOutOfBoundsException(Integer productLimit, Integer quantity) {
        super("Quantity " + quantity + " out of bounds limit " + productLimit, HttpStatus.BAD_REQUEST);
        this.productLimit = productLimit;
        this.quantity = quantity;
    }
    private final Integer productLimit;
    private final Integer quantity;
}
