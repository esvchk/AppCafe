package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.model.Discount;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = OrderItemConverter.class)
public interface DiscountConverter {
    @Mapping(source = "role",target = "roleDTO")
    @Mapping(source = "orderItems",target = "orderItemDTOS")
    DiscountDTO toDiscountDto(Discount discount);

    @Mapping(source = "roleDTO",target = "role")
    @Mapping(source = "orderItemDTOS",target = "orderItems")
    Discount toDiscountEntity(DiscountDTO discountDTO);
}
