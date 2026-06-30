package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Category extends DataEntity {


    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();


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
            this.setProducts(new ArrayList<>());
        }
        this.products.add(product);
        product.setCategory(this);
    }
    public void removeProduct(Product product){
        if (this.getProducts() == null) {
            this.setProducts(new ArrayList<>());
        }
        this.products.remove(product);
        product.setCategory(null);
    }

}