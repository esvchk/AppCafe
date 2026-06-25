package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.model.Discount;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DiscountConverter implements Converter<Discount, DiscountDTO> {

    private final RoleConverter roleConverter;

    @Override
    public DiscountDTO convert(Discount source) {
        return DiscountDTO.builder()
                .id(source.getId())
                .percentOfDiscount(source.getPercentOfDiscount())
                .roles(source.getRoles().stream()
                        .map(roleConverter::convert)
                        .collect(Collectors.toSet()))
                .build();
    }
}
