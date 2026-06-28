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
public class DiscountDTO {

    private Long id;

    private BigDecimal percentOfDiscount;

    private String name;

    private Boolean isActive;

    private List<OrderItemDTO> orderItemDTOS;


}
