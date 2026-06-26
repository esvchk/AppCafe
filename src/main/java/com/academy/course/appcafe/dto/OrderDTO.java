package com.academy.course.appcafe.dto;

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
    private Long id;

    private List<OrderItemDTO> orderItemDTOS;

    private String paymentData;

    private BigDecimal totalCost;

    private Boolean isBought;

    private EmployeeDTO employeeDTO;

}
