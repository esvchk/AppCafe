package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT COALESCE(SUM(order.totalCost),0)from Order order")
    BigDecimal getTotalOrdersAmount();
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.orderDiscount " +
            "LEFT JOIN FETCH o.orderItems " +
            "WHERE o.id = :id")
    Optional<Order> findWithDiscountAndItems(@Param("id") Long id);
}
