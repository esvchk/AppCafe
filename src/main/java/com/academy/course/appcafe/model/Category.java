package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Category extends DataEntity {


    @NotBlank
    @Column
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @Builder.Default
    private Set<Product> products = new HashSet<>();


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Category category = (Category) object;
        return Objects.equals(getId(),category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void addProduct(Product product){
        if (this.getProducts() == null) {
            this.setProducts(new HashSet<>());
        }
        this.products.add(product);
        product.setCategory(this);
    }
    public void removeProduct(Product product){
        if (this.getProducts() == null) {
            this.setProducts(new HashSet<>());
        }
        this.products.remove(product);
        product.setCategory(null);
    }

}