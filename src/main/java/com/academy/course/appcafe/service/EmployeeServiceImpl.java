package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.DiscountConverter;
import com.academy.course.appcafe.converter.EmployeeConverter;
import com.academy.course.appcafe.converter.OrderConverter;
import com.academy.course.appcafe.converter.RoleConverter;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeRequest;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.model.Role;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeConverter employeeConverter;
    private final RoleConverter roleConverter;
    private final OrderConverter orderConverter;
    private final OrderService orderService;


    @Override
    public void deleteOrderOfEmployee(Long id, OrderDTO orderDTO) throws SQLException {
        if (employeeRepository.existsById(id)) {
            Employee employee = employeeRepository.getReferenceById(id);
            Order orderToRemove = employee.getOrders().stream()
                    .filter(order -> order.getIsBought() == false)
                    .findFirst().orElse(null);
            employee.getOrders().remove(orderToRemove);
            employeeRepository.save(employee);
        }
    }

    @Override
    public List<EmployeeDTO> getFullListOfEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeConverter::toEmployeeDTO)
                .toList();
    }

    @Override
    public Page<EmployeeDTO> getPaginatedListOfEmployees(int offset, int size) {
        if (offset < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(offset, size);
        Page<Employee> page = employeeRepository.findAll(pageable);
        List<EmployeeDTO> employeeDTOS = page.getContent().stream()
                .map(employeeConverter::toEmployeeDTO)
                .toList();

        return new PageImpl<>(employeeDTOS, pageable, page.getTotalElements());
    }

    @Override
    public EmployeeDTO findEmployeeById(Long id) throws SQLException {
        if (employeeRepository.existsById(id)) {
            return employeeConverter.toEmployeeDTO(employeeRepository.getReferenceById(id));
        }
        return null;
    }

    @Override
    public EmployeeDTO findEmployeeByLogin(String login) {
        if (employeeRepository.existsByLogin(login)) {
            return employeeConverter.toEmployeeDTO(employeeRepository.findByLogin(login));
        }
        return null;
    }

    @Override
    public void addNewOrderToEmployee(Long id) throws SQLException {
        if (employeeRepository.existsById(id)) {
            Employee employee = employeeRepository.getReferenceById(id);
            Order order = Order.builder()
                    .isBought(false)
                    .employee(employee)
                    .build();
            employee.addOrder(order);
            employeeRepository.save(employee);
        }
//        logger.info("Order {} successfully created to Employee {}",order,employeeDTO);
    }

    @Override
    public boolean registerEmployee(EmployeeRequest employeeRequest) throws SQLException {

        Employee employee = Employee.builder()
                .login(employeeRequest.getLogin())
                .password(PasswordEncoder.hashPass(employeeRequest.getPassword()))
                .build();

//        Order order = Order.builder()
//                .employee(employee)
//                .isBought(false)
//                .build();

        List<Role> roles = roleRepository.findAllByNameIn(employeeRequest.getRoleNames());

        roles.forEach(employee::addRole);

//        employee.addOrder(order);

        employeeRepository.save(employee);

//        logger.info("Employee {} successfully registered",login);
        return true;

    }

    @Override
    public void updateEmployee(Long oldValueId, EmployeeDTO newValue) throws SQLException {
        if (employeeRepository.existsById(oldValueId)) {
            Employee employee = employeeRepository.getReferenceById(oldValueId);
            employee.setLogin(newValue.getLogin());
            employee.setRoles(newValue.getRoleDTOS().stream()
                    .map(roleConverter::toRoleEntity)
                    .collect(Collectors.toSet()));
            employeeRepository.save(employeeConverter.toEntityEmployee(newValue));
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) throws SQLException {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        }
    }

    @Override
    public boolean login(String login, String passWord) throws NoSuchFieldException, SQLException {
        return false;
    }

    @Override
    public Double getTotalAmountOfOrders() throws SQLException {
        List<OrderDTO> orderDTOS = orderService.getAllOrdersWithItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderDTO orderDTO : orderDTOS) {
            if (orderDTO.getIsBought()) {
                BigDecimal orderAmount = BigDecimal.valueOf(orderService.countAmountOfAllItems(orderDTO.getId()));
                totalAmount = totalAmount.add(orderAmount);
            }
        }
        return totalAmount.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public OrderDTO getCurrentOrderOfEmployee(String login) {
        if (employeeRepository.existsByLogin(login)) {
            Employee employee = employeeRepository.findByLogin(login);
            List<Order> orderList = employee.getOrders();
            Order currentOrder = orderList.stream().filter(order -> order.getIsBought() == false)
                    .findFirst().orElse(null);
            return orderConverter.toOrderDTO(currentOrder);
        }
        return null;
    }
}
