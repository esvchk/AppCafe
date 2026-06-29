package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByName(String name);

    List<Product> findAllByName(String name);

    Page<Product> findAllByIsAvailableTrue(Boolean isAvailable, Pageable pageable);

    List<Product> findAllByIsAvailable(Boolean isAvailable);
}
