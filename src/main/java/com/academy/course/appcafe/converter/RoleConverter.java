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
    private final EmployeeConverter employeeConverter;
    private final DiscountConverter discountConverter;

    @Override
    public RoleDTO convert(Role source) {
        return RoleDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .employeeDTOS(source.getEmployees().stream()
                        .map(employeeConverter::convert)
                        .collect(Collectors.toSet()))
                .discountDTOS(source.getDiscounts().stream()
                        .map(discountConverter::convert)
                        .collect(Collectors.toSet()))
                .build();
    }
}
