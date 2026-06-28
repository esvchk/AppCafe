package com.academy.course.appcafe.dto;

import com.academy.course.appcafe.model.Discount;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
