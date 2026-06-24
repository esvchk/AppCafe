package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item", schema = "internet_shop")
public class Item {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "productQuantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "discount")
    private Integer discount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


}