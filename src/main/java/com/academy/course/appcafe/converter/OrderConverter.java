package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderConverter implements Converter<Order, OrderDTO> {
    private final OrderItemConverter orderItemConverter;
    @Override
    public OrderDTO convert(Order source) {
        return OrderDTO.builder()
                .id(source.getId())
                .orderItemDTOS(source.getOrderItems().stream()
                        .map(orderItemConverter::convert)
                        .collect(Collectors.toSet()))
                .paymentData(source.getPaymentData())
                .totalCost(source.getTotalCost())
                .isBought(source.getIsBought())
                .build();
    }
}
