package com.academy.course.appcafe.dto;

import com.academy.course.appcafe.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;

    private Set<OrderItemDTO> orderItemDTOS;

    private String paymentData;

    private BigDecimal totalCost;

    private Boolean isBought;
}
