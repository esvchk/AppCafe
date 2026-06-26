package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.model.Employee;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Mapper(componentModel = "spring",uses = {RoleConverter.class, OrderConverter.class})
public interface EmployeeConverter  {


    @Mapping(source = "roles",target = "roleDTOS")
    @Mapping(source = "orders",target = "orderDTOS")
    EmployeeDTO toEmployeeDTO(Employee employee);


    @Mapping(source = "roleDTOS",target = "roles")
    @Mapping(source = "orderDTOS",target = "orders")
    Employee toEntityEmployee(EmployeeDTO employeeDTO);




}
