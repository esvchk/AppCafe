package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Employee extends DataEntity{


    @Column(nullable = false,unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();


//    @Override
//    public boolean equals(Object object) {
//        if (object == null || getClass() != object.getClass()) return false;
//        if (!super.equals(object)) return false;
//        Employee employee = (Employee) object;
//        return Objects.equals(employee.getId(), getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }

    public void addOrder(Order order) {
        if (order == null) return;
        this.orders.add(order);
        order.setEmployee(this);
    }

    public void addRole(Role role) {
        if (role == null || this.roles.contains(role)) {
            return;
        }
        this.roles.add(role);
        if (role.getEmployees() != null) {
            role.getEmployees().add(this);
        }
    }


}