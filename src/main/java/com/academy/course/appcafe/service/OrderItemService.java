package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.OrderItemDTO;
import org.springframework.data.domain.Page;

import java.sql.SQLException;

public interface OrderItemService {
    OrderItemDTO getOrderItemById(Long orderItemId) throws SQLException;
    void deleteItem(Long orderItemId) throws SQLException;
    Page<OrderItemDTO> getPaginatedItems(int page,int size);
    Page<OrderItemDTO> getAllItemsFromOrder(int page,int size,Long orderId) throws SQLException;
    void countAmountOfItem(Long itemId,Long discountId) throws SQLException;
}
