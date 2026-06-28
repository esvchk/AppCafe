package com.academy.course.appcafe.dto;

import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithAllRolesToEdit {

    private EmployeeDTO employeeDTO;
    private Set<RoleDTO> allRolesDTOSToChoose;
}
