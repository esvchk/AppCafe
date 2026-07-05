package com.academy.course.appcafe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderRequest {

    @NotNull(message = "Id cannot be null")
    @Positive(message = "Id must be positive")
    private Long orderId;

    @NotNull(message = "Id cannot be null")
    @Positive(message = "Id must be positive")
    private Long productId;

    @NotNull(message = "quantity cannot be empty")
    private Integer quantity;



}
