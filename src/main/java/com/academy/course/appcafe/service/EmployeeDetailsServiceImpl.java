package com.academy.course.appcafe.service;

import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements UserDetailsService{

    private final EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(@NonNull String userName) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByLogin(userName);
        return new EmployeeDetailsImpl(employee);
    }
}
