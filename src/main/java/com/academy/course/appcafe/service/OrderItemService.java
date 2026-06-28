package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.model.Discount;
import com.academy.course.appcafe.model.Order;
import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface OrderItemService {
    OrderItemDTO getOrderItemById(Long orderItemId) throws SQLException;
    void deleteItem(Long orderItemId) throws SQLException;
    Page<OrderItemDTO> getPaginatedItems(int page,int size);
    Page<OrderItemDTO> getAllItemsFromOrder(int page,int size,Long orderId) throws SQLException;
    void setDiscountOnItem(Long itemId,Long discountId) throws SQLException;
}
