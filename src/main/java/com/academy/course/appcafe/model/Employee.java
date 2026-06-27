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

    @NotBlank
    @Column
    private String login;

    @NotBlank
    @Column
    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

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

    public void addOrder(Order order){
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
        this.orders.add(order);
        order.setEmployee(this);
    }

    public void addRole(Role role){
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
        role.getEmployees().add(this);
    }


}