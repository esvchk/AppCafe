package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.OrderDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    OrderDTO findOrderById(Long orderId) ;
    OrderDTO addNewOrderToEmployeeByLogin(String login);
    Page<OrderDTO> getPaginatedListOfOrders(int page,int size);
    List<OrderDTO> getAllOrders();
    void buyOrder(Long orderId);
    void addProductToOrder(Long productId,Long orderId, Integer quantity) ;
    void deleteItemFromOrder(Long itemId,Long orderId,Integer quantity);
    BigDecimal countTotalAmountOfOrders();
    BigDecimal countAmountOfOrder(Long orderId) ;
    void setDiscountOnOrder(Long orderId,BigDecimal percent);
    void inputPaymentDataToOrder(String paymentData,Long orderId);

}
