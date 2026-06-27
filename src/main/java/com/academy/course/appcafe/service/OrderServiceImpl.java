package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.OrderItemConverter;
import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.repository.OrderItemRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderItemRepository orderItemRepository;
    private final OrderItemConverter orderItemConverter;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;


    @Override
    public OrderDTO findOrderById(Integer orderId) throws SQLException {
        return null;
    }

    @Override
    public List<OrderDTO> getAllOrdersWithItems() {
        return List.of();
    }

    @Override
    public void buyOrder(OrderDTO orderDTO) throws SQLException {

    }

    @Override
    public void deleteOrder(Integer id) throws SQLException {

    }

    @Override
    public void addProductToOrder(Integer productId, Integer orderId, Integer quantity) throws SQLException {

    }

    @Override
    public void deleteItemFromOrder(Long itemId, Integer orderId, Integer quantity) throws SQLException {

    }

    @Override
    public Double countAmountOfAllItems(Long orderId) throws SQLException {
        if (orderRepository.existsById(orderId)) {
            Order order = orderRepository.getReferenceById(orderId);
            List<OrderItemDTO> items = orderItemRepository.findAll().stream()
                    .map(orderItemConverter::toOrderItemDTO)
                    .toList();
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderItemDTO orderItemDTO : items){
                BigDecimal itemAmount = BigDecimal.valueOf(orderItemDTO.getProductDTO().getPrice() * orderItemDTO.getQuantity());
                totalPrice = totalPrice.add(itemAmount.multiply(orderItemDTO.getDiscount().getPercentOfDiscount()));
                order.setTotalCost(totalPrice.doubleValue());
            }
            orderRepository.save(order);
            return order.getTotalCost();
        }
        return null;
    }

    @Override
    public void setDiscountOnOrder(Long orderId, DiscountDTO discount) throws SQLException {
        if (orderRepository.existsById(orderId)) {
            List<OrderItemDTO> items = orderItemRepository.findAll().stream()
                    .map(orderItemConverter::toOrderItemDTO)
                    .toList();
            for (OrderItemDTO itemDTO : items){
                orderItemService.setDiscountOnItem(itemDTO.getId(),discount);
            }
        }
    }
}
