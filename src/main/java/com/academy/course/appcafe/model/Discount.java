package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Discount extends DataEntity{

    @Column
    private BigDecimal percentOfDiscount;

    @Column(nullable = false)
    private String name;

    @Column
    Boolean isActive;


    @OneToMany(mappedBy = "appliedDiscount",cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private List<OrderItem> orderItems;
}
