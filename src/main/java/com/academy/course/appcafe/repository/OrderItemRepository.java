package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findAllByOrderIs(Order order);

    Page<OrderItem> findAllByOrderIs(Order order, Pageable pageable);
}
