package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.OrderDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderDTO findOrderById(Long orderId) throws SQLException;
    Page<OrderDTO> getPaginatedListOfOrders(int page,int size);
    List<OrderDTO> getAllOrders();
    void buyOrder(Long orderId) throws SQLException;
    void deleteOrder(Long id) throws SQLException;
    void addProductToOrder(Long productId,Long orderId, Integer quantity) throws SQLException;
    void deleteItemFromOrder(Long itemId,Long orderId,Integer quantity) throws SQLException;
    BigDecimal countAmountOfOrder(Long orderId) throws SQLException;
    void setDiscountOnOrder(Long orderId, Long discountId) throws SQLException;
}
