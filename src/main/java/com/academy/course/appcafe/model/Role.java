package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @OneToMany(mappedBy = "role",fetch = FetchType.EAGER)
    List<Employee> employees;

    @OneToOne(mappedBy = "role",fetch = FetchType.EAGER)
    Discount discount;

}
