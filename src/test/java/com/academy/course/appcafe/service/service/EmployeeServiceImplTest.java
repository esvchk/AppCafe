package com.academy.course.appcafe.service.service;

import com.academy.course.appcafe.converter.EmployeeConverter;
import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeRequest;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.RoleRepository;
import com.academy.course.appcafe.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EmployeeConverter employeeConverter;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void findEmployeeByIdTest(){
        Long id = 1L;
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .login("LOGIN")
                .build();

        Employee employee = new Employee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeConverter.toEmployeeDTO(any())).thenReturn(employeeDTO);
        EmployeeDTO returnedEmployee = employeeService.findEmployeeById(id);
        assertNotNull(returnedEmployee);
        assertEquals(employeeDTO.getLogin(),returnedEmployee.getLogin());
        verify(employeeRepository,times(1)).findById(id);
    }

    @Test
    void findEmployeeByIdThrowsException(){

        Long id = 1L;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundByIdException.class,() -> {
            employeeService.findEmployeeById(id);
        });

        verify(employeeRepository,times(1)).findById(id);
        verifyNoInteractions(employeeConverter);
    }


}
