package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.EmployeeConverter;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;


    @Override
    public void deleteOrderOfEmployee(EmployeeDTO employeeDTO, OrderDTO orderDTO) throws SQLException {

    }

    @Override
    public List<EmployeeDTO> getPaginatedListOfEmployees() {
        return List.of();
    }

    @Override
    public Page<EmployeeDTO> getPaginatedListOfEmployees(int offset, int size) {
        if (offset < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(offset,size);
        Page<Employee> page = employeeRepository.findAll(pageable);
        List<EmployeeDTO> employeeDTOS = page.getContent().stream()
                .map(employeeConverter::toEmployeeDTO)
                .toList();

        return new PageImpl<>(employeeDTOS,pageable,page.getTotalElements());
    }

    @Override
    public EmployeeDTO findEmployeeById(Long id) throws SQLException {
        return null;
    }

    @Override
    public EmployeeDTO findEmployeeByLogin(String login) {
        return null;
    }

    @Override
    public void addNewOrderToEmployee(EmployeeDTO employeeDTO) throws SQLException {

    }

    @Override
    public boolean registerEmployee(String login, String pass, RoleDTO role) throws SQLException {
        return false;
    }

    @Override
    public void updateEmployee(Long oldValueId, EmployeeDTO newValue) throws SQLException {

    }

    @Override
    public void deleteEmployee(Long employeeId) throws SQLException {

    }

    @Override
    public boolean login(String login, String passWord) throws NoSuchFieldException, SQLException {
        return false;
    }

    @Override
    public BigDecimal getTotalAmountOfOrders() throws SQLException {
        return null;
    }

    @Override
    public OrderDTO getCurrentOrderOfEmployee(String login) {
        return null;
    }
}
