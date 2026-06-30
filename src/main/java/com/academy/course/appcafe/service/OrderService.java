package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.OrderDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    OrderDTO findOrderById(Long orderId) throws SQLException;
    OrderDTO addNewOrderToEmployeeByLogin(String login);
    Page<OrderDTO> getPaginatedListOfOrders(int page,int size);
    List<OrderDTO> getAllOrders();
    void buyOrder(Long orderId) throws SQLException;
    void addProductToOrder(Long productId,Long orderId, Integer quantity) throws SQLException;
    void deleteItemFromOrder(Long itemId,Long orderId,Integer quantity) throws SQLException;
    BigDecimal countTotalAmountOfOrders() throws SQLException;
    BigDecimal countAmountOfOrder(Long orderId) throws SQLException;
    void setDiscountOnOrder(Long orderId,BigDecimal percent);
    void inputPaymentDataToOrder(String paymentData,Long orderId);

}
