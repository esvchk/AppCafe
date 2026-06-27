package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.RoleDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {

    void deleteOrderOfEmployee(Long id, OrderDTO orderDTO) throws SQLException;
    List<EmployeeDTO> getFullListOfEmployees() ;
    Page<EmployeeDTO> getPaginatedListOfEmployees(int offset, int size);
    EmployeeDTO findEmployeeById(Long id) throws SQLException;
    EmployeeDTO findEmployeeByLogin(String login);
    void addNewOrderToEmployee(Long id) throws SQLException;
    boolean registerEmployee(String login, String pass, RoleDTO role) throws SQLException;
    void updateEmployee(Long oldValueId, EmployeeDTO newValue) throws SQLException;
    void deleteEmployee(Long employeeId) throws SQLException;
    boolean login(String login,String passWord) throws NoSuchFieldException, SQLException;
    Double getTotalAmountOfOrders() throws SQLException;
    OrderDTO getCurrentOrderOfEmployee(String login);
}
