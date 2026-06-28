package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT COALESCE(SUM(order.totalCost),0)from Order order")
    BigDecimal getTotalOrdersAmount();
}
