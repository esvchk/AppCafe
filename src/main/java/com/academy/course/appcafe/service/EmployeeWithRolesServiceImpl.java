package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.EmployeeConverter;
import com.academy.course.appcafe.converter.RoleConverter;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeWithAllRolesToEdit;
import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class EmployeeWithRolesServiceImpl implements EmployeeWithRolesService{

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;
    private final EmployeeConverter employeeConverter;

    @Override
    public EmployeeWithAllRolesToEdit getPairByEmployeeId(Long id) {
        Set<RoleDTO> roles = roleRepository.findAll().stream()
                .map(roleConverter::toRoleDTO)
                .collect(Collectors.toSet());
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(id));
        EmployeeDTO employeeDTO = employeeConverter.toEmployeeDTO(employee);
        return EmployeeWithAllRolesToEdit.builder()
                .employeeDTO(employeeDTO)
                .roleDTOS(roles)
                .build();
    }
}
