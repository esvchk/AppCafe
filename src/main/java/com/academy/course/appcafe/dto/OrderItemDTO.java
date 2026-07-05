package com.academy.course.appcafe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    @Min(value = 1,message = "Id must be positive and not equal 0")
    private Long id;

    @Valid
    @NotNull(message = "Product cannot be empty")
    private ProductDTO productDTO;

    @Valid
    @NotNull(message = "Order cannot be null")
    private OrderDTO orderDTO;

    @NotBlank(message = "Cannot be empty")
    @Pattern(regexp = "(.*[0-9].*)",message = "Product quantity may contain only figures")
    private Integer productQuantity;

}
