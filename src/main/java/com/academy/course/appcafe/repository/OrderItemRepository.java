package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
