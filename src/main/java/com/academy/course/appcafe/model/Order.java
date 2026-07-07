package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Column(nullable = false)
    private String paymentData;

    @Column(nullable = true)
    private BigDecimal totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = true)
    private BigDecimal percentOfDiscount;

    @Column(nullable = false,unique = true)
    private Boolean isBought;


    public void addOrderItem(OrderItem item){
        if (this.getOrderItems()==null){
            this.setOrderItems(new ArrayList<>());
        }
        this.orderItems.add(item);
        item.setOrder(this);
    }




}