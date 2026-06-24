package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Role extends DataEntity{
    @Column
    private String name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    Set<Employee> employees;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    Set<Discount> discounts;

}
