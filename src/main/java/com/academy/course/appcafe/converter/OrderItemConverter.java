package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring")
public interface OrderItemConverter{

    @Mapping(source = "product",target = "productDTO")
    @Mapping(source = "order",target = "orderDTO")
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(source = "productDTO",target = "product")
    @Mapping(source = "orderDTO",target = "order")
    OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO);
}
