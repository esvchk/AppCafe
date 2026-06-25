package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RoleConverter implements Converter<Role, RoleDTO> {
    private final DiscountConverter discountConverter;

    @Override
    public RoleDTO convert(Role source) {
        return RoleDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .discountDTO(discountConverter.convert(source.getDiscount()))
                .build();
    }
}
