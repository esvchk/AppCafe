package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.model.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter implements Converter<Employee, EmployeeDTO> {
    @Override
    public EmployeeDTO convert(Employee source) {
        return EmployeeDTO.builder()
                .id(source.getId())
                .login(source.getLogin())
//                .roles()
//                .orderDTOs()
                .build();
    }
}
