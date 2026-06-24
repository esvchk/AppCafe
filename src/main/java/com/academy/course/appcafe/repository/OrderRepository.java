package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
