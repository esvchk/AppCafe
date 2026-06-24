package com.academy.course.appcafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "internet_shop")
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "paymentData")
    private String paymentData;

    @Column(name = "totalCost")
    private Double totalCost;

    @Column(name = "isBought", nullable = false)
    private Boolean isBought;


}