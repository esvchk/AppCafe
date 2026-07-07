package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.EmployeeConverter;
import com.academy.course.appcafe.converter.RoleConverter;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeEdit;
import com.academy.course.appcafe.dto.EmployeeWithAllRolesToEdit;
import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Role;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class EmployeeWithRolesServiceImpl implements EmployeeWithRolesService{

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Override
    public EmployeeWithAllRolesToEdit getPairByEmployeeId(Long id) {
        Set<RoleDTO> allRoles = roleRepository.findAll().stream()
                .map(roleConverter::toRoleDTO)
                .collect(Collectors.toSet());


        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(id));

        List<Long> currentRoleIds = employee.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        return EmployeeWithAllRolesToEdit.builder()
                .id(employee.getId())
                .login(employee.getLogin())
                .roleDTOS(allRoles)
                .roleIds(currentRoleIds)
                .build();
    }

}
