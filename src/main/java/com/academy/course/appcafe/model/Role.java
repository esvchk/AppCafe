package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Role extends DataEntity {
    @Column
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
            @Builder.Default
    Set<Employee> employees = new HashSet<>();


    public void addEmployee(Employee employee) {
        if (employee == null||this.employees.contains(employee)) {
            return;
        }
        this.employees.add(employee);
    }
}
