package com.academy.course.appcafe.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotNull(message = "Id required")
    @Min(value = 1,message = "Id must be positive and not equal 0")
    private Long id;

    @Nullable
    @Valid
    private List<OrderItemDTO> orderItemDTOS;

    @NotNull(message = "Payment data cannot be empty")
    private String paymentData;

    @DecimalMin(value = "0.01",message = "Min percent 0.01")
    @DecimalMax(value = "99.9",message = "Min percent 99.9")
    @Pattern(regexp = "(.*[0-9].*)")
    private BigDecimal percentOfDiscount;

    @NotNull
    @DecimalMin(value = "0.0",message = "Total cost must be positive")
    private BigDecimal totalCost;

    @NotNull
    private Boolean isBought;

    @Valid
    @NotNull(message = "Employee cannot be null")
    private EmployeeDTO employeeDTO;

}
