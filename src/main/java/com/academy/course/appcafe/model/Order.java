package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order extends DataEntity{

    @ToString.Exclude
    @OneToMany(mappedBy = "order",cascade = {CascadeType.MERGE,CascadeType.REMOVE},orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column
    private String paymentData;

    @Column(nullable = false)
    private BigDecimal totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderDiscount_id")
    private Discount orderDiscount;

    @Column
    private BigDecimal percentOfDiscount;

    @Column
    private Boolean isBought;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(getId(),order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }



}