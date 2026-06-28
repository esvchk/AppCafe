package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.OrderConverter;
import com.academy.course.appcafe.converter.OrderItemConverter;
import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.model.Discount;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.model.OrderItem;
import com.academy.course.appcafe.repository.DiscountRepository;
import com.academy.course.appcafe.repository.OrderItemRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderItemRepository orderItemRepository;
    private final OrderItemConverter orderItemConverter;
    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final DiscountRepository discountRepository;


    @Override
    public OrderDTO findOrderById(Long orderId) throws SQLException {
        if (orderRepository.existsById(orderId)) {
            return orderConverter.toOrderDTO(orderRepository.getReferenceById(orderId));
        }
        return null;
    }

    @Override
    public Page<OrderDTO> getPaginatedListOfOrders(int page,int size) {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<OrderDTO> orderDTOS = orderPage.getContent().stream()
                .map(orderConverter::toOrderDTO)
                .toList();

        return new PageImpl<>(orderDTOS, pageable, orderPage.getTotalElements());

    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderConverter::toOrderDTO)
                .toList();
    }


    @Override
    public void buyOrder(Long orderId) throws SQLException {

    }

    @Override
    public void deleteOrder(Long orderId) throws SQLException {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        }
    }

    @Override
    public void addProductToOrder(Long productId, Long orderId, Integer quantity) throws SQLException {

    }

    @Override
    public void deleteItemFromOrder(Long itemId, Long orderId, Integer quantity) throws SQLException {

    }

    @Override
    public BigDecimal countAmountOfOrder(Long orderId) throws SQLException {
        return orderRepository.getTotalOrdersAmount();
    }

    @Override
    public void setDiscountOnOrder(Long orderId, Long discountId) throws SQLException {
        boolean isOrderExists = orderRepository.existsById(orderId);
        boolean isDiscountExists = orderRepository.existsById(discountId);
        if (isOrderExists) {
            Order order = orderRepository.getReferenceById(orderId);
            Discount discount = discountRepository.getReferenceById(discountId);
            BigDecimal percent = (isDiscountExists) ? discount.getPercentOfDiscount() : BigDecimal.ZERO;
            order.setPercentOfDiscount(percent);

            BigDecimal subTotal = order.getOrderItems().stream()
                    .map(OrderItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO,BigDecimal::add);

            BigDecimal factor = BigDecimal.ONE
                    .subtract(percent.divide(BigDecimal.valueOf(100),4, RoundingMode.HALF_UP));

            BigDecimal total = subTotal
                    .multiply(factor)
                    .setScale(2,RoundingMode.HALF_UP);

            order.setTotalCost(total);
            orderRepository.save(order);
        }


    }
}
