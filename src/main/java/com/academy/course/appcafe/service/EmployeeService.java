package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.*;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    Page<EmployeeDTO> getPaginatedListOfEmployees(int offset, int size);
    EmployeeDTO findEmployeeById(Long id) ;
    EmployeeDTO findEmployeeByLogin(String login);
    void registerEmployee(EmployeeRequest employeeRequest) ;
    void updateEmployee(Long oldValueId, EmployeeEdit employeeEdit);
    void deleteEmployee(Long employeeId);
}
