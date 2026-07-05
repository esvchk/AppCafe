package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.EmployeeEdit;
import com.academy.course.appcafe.dto.EmployeeWithAllRolesToEdit;

public interface EmployeeWithRolesService {
    EmployeeWithAllRolesToEdit getPairByEmployeeId(Long id);

}
