package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    boolean existsByLogin(String login);

    List<Employee> getEmployeeByLogin(String login);

    Employee findByLogin(String login);
}
