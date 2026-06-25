package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.model.Discount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DiscountConverter implements Converter<Discount, DiscountDTO> {
    @Override
    public DiscountDTO convert(Discount source) {
        return DiscountDTO.builder()
                .id(source.getId())
                .percentOfDiscount(source.getPercentOfDiscount())
//                .roles()
                .build();
    }
}
