package com.academy.course.appcafe.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Product extends DataEntity{

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String info;

    @Column
    private Boolean isAvailable;

    @Column
    private Integer productLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(getId(),product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}