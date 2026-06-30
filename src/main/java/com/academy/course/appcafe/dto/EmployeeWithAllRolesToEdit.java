package com.academy.course.appcafe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithAllRolesToEdit {

    @NotNull(message = "Employee should exist")
    @Valid
    private EmployeeDTO employeeDTO;

    @Valid
    @NotNull(message = "Roles cannot be empty")
    private Set<RoleDTO> roleDTOS = new HashSet<>();
}
