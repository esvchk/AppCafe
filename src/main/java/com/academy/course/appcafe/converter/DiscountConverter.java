package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.DiscountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = OrderItemConverter.class)
public interface DiscountConverter {
    @Mapping(source = "orderItems",target = "orderItemDTOS")
    DiscountDTO toDiscountDto(Discount discount);


    @Mapping(source = "orderItemDTOS",target = "orderItems")
    Discount toDiscountEntity(DiscountDTO discountDTO);
}
