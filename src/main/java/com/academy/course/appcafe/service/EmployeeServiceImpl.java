package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.EmployeeConverter;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeEdit;
import com.academy.course.appcafe.dto.EmployeeRequest;
import com.academy.course.appcafe.exception.EmptyEntityException;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.exception.EntityNotFoundByNameException;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.model.Role;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import com.academy.course.appcafe.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeConverter employeeConverter;
    private final OrderRepository orderRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> getPaginatedListOfEmployees(int page, int size) {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        List<EmployeeDTO> employeeDTOS = employeePage.getContent().stream()
                .map(employeeConverter::toEmployeeDTO)
                .toList();

        return new PageImpl<>(employeeDTOS, pageable, employeePage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(id));
        return employeeConverter.toEmployeeDTO(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO findEmployeeByLogin(String login) {
        Employee employee = employeeRepository.findByLogin(login);
        if (employee != null) {
            return employeeConverter.toEmployeeDTO(employee);
        }
        throw new EntityNotFoundByNameException(login);
    }

    @Override
    public void addNewOrderToEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(id));
            Order order = Order.builder()
                    .isBought(false)
                    .employee(employee)
                    .build();
            employee.addOrder(order);
            employeeRepository.save(employee);
    }

    @Override
    public boolean registerEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest == null) {
            throw new EmptyEntityException(employeeRequest);
        }

        Employee employee = Employee.builder()
                .login(employeeRequest.getLogin())
                .password(PasswordEncoder.hashPass(employeeRequest.getPassword()))
                .build();

        List<Role> roles = roleRepository.findAllByNameIn(employeeRequest.getRoleNames());
        if (roles == null || roles.isEmpty()) {
            throw new EmptyEntityException(roles);
        }

        roles.forEach(employee::addRole);

        employeeRepository.save(employee);

        return true;

    }

    @Override
    public void updateEmployee(Long oldValueId, EmployeeEdit employeeEdit) {
        Employee employee = employeeRepository.findById(oldValueId).orElseThrow(() -> new EntityNotFoundByIdException(oldValueId));
            employee.setLogin(employeeEdit.getLogin());
            if (!employeeEdit.getRoleIds().isEmpty()) {
                List<Role> roles = roleRepository.findAllById(employeeEdit.getRoleIds());
                employee.setRoles(new HashSet<>(roles));
            } else {
                throw new EmptyEntityException(employeeEdit);
            }
            employeeRepository.save(employee);

    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundByIdException(employeeId));
            employeeRepository.deleteById(employee.getId());

    }

}
