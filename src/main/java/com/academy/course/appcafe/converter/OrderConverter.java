package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.model.Order;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = OrderItemConverter.class)
public interface OrderConverter {

    @Mapping(source = "orderItems",target = "orderItemDTOS")
//    @Mapping(source = "employee",target = "employeeDTO")
    OrderDTO toOrderDTO(Order order);

    @Mapping(source = "orderItemDTOS",target = "orderItems")
//    @Mapping(source = "employeeDTO",target = "employee")
    Order toOrderEntity(OrderDTO orderDTO);
}
