package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
