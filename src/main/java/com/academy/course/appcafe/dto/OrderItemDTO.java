package com.academy.course.appcafe.dto;

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

    private Long id;

    private ProductDTO productDTO;

    private OrderDTO orderDTO;

    private Integer productQuantity;

    private Discount appliedDiscount;

    private BigDecimal appliedPercent;

    private BigDecimal priceBeforeDiscount;

    private BigDecimal totalPrice;
}
