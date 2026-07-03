package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    boolean existsByLogin(String login);

    List<Employee> getEmployeeByLogin(String login);

    Optional<Employee> findByLogin(String login);
}
