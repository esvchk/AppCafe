package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class OrderItemConverter implements Converter<OrderItem, OrderItemDTO> {
    private final ProductConverter productConverter;
    @Override
    public OrderItemDTO convert(OrderItem source) {
        return OrderItemDTO.builder()
                .id(source.getId())
                .productDTO(productConverter.convert(source.getProduct()))
                .quantity(source.getProductQuantity())
                .discount(source.getDiscount())
                .build();
    }
}
