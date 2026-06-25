package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class EmployeeConverter implements Converter<Employee, EmployeeDTO> {

    private final RoleConverter roleConverter;
    private final OrderConverter orderConverter;

    @Override
    public EmployeeDTO convert(Employee source) {
        return EmployeeDTO.builder()
                .id(source.getId())
                .login(source.getLogin())
                .roleDTO(roleConverter.convert(source.getRole()))
                .orderDTOs(source.getOrders().stream()
                        .map(orderConverter::convert)
                        .collect(Collectors.toList()))
                .build();
    }
}
