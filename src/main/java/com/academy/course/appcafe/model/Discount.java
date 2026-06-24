package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Discount extends DataEntity{

    private BigDecimal percentOfDiscount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "discount_role",
            joinColumns = @JoinColumn(name = "discount_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
