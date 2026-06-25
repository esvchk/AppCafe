package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.model.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter implements Converter<Order, OrderDTO> {
    @Override
    public OrderDTO convert(Order source) {
        return OrderDTO.builder()
                .id(source.getId())
//                .orderItemDTOS()
                .paymentData(source.getPaymentData())
                .totalCost(source.getTotalCost())
                .isBought(source.getIsBought())
                .build();
    }
}
