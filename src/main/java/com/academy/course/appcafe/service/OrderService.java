package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.OrderDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderDTO findOrderById(Integer orderId) throws SQLException;
    List<OrderDTO> getAllOrdersWithItems();
    void buyOrder(OrderDTO orderDTO) throws SQLException;
    void deleteOrder(Integer id) throws SQLException;
    void addProductToOrder(Integer productId,Integer orderId, Integer quantity) throws SQLException;
    void deleteItemFromOrder(Long itemId,Integer orderId,Integer quantity) throws SQLException;
    BigDecimal countAmountOfAllItems(Long orderId) throws SQLException;
    void setDiscountOnOrder(Long orderId, DiscountDTO discount) throws SQLException;
}
