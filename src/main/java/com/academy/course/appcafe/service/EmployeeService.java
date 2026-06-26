package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.RoleDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {

    void deleteOrderOfEmployee(EmployeeDTO employeeDTO, OrderDTO orderDTO) throws SQLException;
    List<EmployeeDTO> getPaginatedListOfEmployees() ;
    Page<EmployeeDTO> getPaginatedListOfEmployees(int offset, int size);
    EmployeeDTO findEmployeeById(Long id) throws SQLException;
    EmployeeDTO findEmployeeByLogin(String login);
    void addNewOrderToEmployee(EmployeeDTO employeeDTO) throws SQLException;
    boolean registerEmployee(String login, String pass, RoleDTO role) throws SQLException;
    void updateEmployee(Long oldValueId, EmployeeDTO newValue) throws SQLException;
    void deleteEmployee(Long employeeId) throws SQLException;
    boolean login(String login,String passWord) throws NoSuchFieldException, SQLException;
    BigDecimal getTotalAmountOfOrders() throws SQLException;
    OrderDTO getCurrentOrderOfEmployee(String login);
}
