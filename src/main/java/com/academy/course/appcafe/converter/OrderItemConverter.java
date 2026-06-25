package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.model.OrderItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter implements Converter<OrderItem, OrderItemDTO> {
    @Override
    public OrderItemDTO convert(OrderItem source) {
        return OrderItemDTO.builder()
                .id(source.getId())
//                .productDTO()
                .quantity(source.getProductQuantity())
                .discount(source.getDiscount())
                .build();
    }
}
