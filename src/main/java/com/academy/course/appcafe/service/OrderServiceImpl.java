package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.OrderConverter;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.model.*;
import com.academy.course.appcafe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderItemRepository orderItemRepository;
    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final EmployeeRepository employeeRepository;
    private final OrderItemService orderItemService;


    @Override
    public OrderDTO findOrderById(Long orderId) throws SQLException {
        if (orderRepository.existsById(orderId)) {
            return orderConverter.toOrderDTO(orderRepository.getReferenceById(orderId));
        }
        return null;
    }

    @Override
    public OrderDTO addNewOrderToEmployeeByLogin(String login) {
        if (employeeRepository.existsByLogin(login)) {
            Employee employee = employeeRepository.findByLogin(login);
            Order order = Order.builder()
                    .employee(employee)
                    .isBought(false)
                    .build();
            employee.addOrder(order);

            orderRepository.save(order);
            return orderConverter.toOrderDTO(order);
        }
        return null;
    }

    @Override
    public Page<OrderDTO> getPaginatedListOfOrders(int page, int size) {
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
        if (orderRepository.existsById(orderId)) {
        }

//        logger.info("Order {} has been successfully bought", orderDTO);
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


//        logger.info("Product {} has been successfully added to order {} with quantity {} "
//                , product, order, quantity);


    @Override
    public void deleteItemFromOrder(Long itemId, Long orderId, Integer quantity) throws SQLException {
        boolean isOrderExists = orderRepository.existsById(orderId);
        boolean isOrderItemExists = orderItemRepository.existsById(itemId);

        if (isOrderItemExists && isOrderExists) {
            Order order = orderRepository.getReferenceById(orderId);
            OrderItem item = orderItemRepository.getReferenceById(itemId);
            if (item.getProductQuantity().equals(quantity)) {
                orderItemRepository.delete(item);
            } else {
                item.setProductQuantity(item.getProductQuantity() - quantity);
            }
            orderRepository.save(order);
        }
    }

    @Override
    public BigDecimal countTotalAmountOfOrders() throws SQLException {
        return orderRepository.getTotalOrdersAmount();
    }

    @Override
    public void countAmountOfOrder(Long orderId) throws SQLException {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            BigDecimal percent = BigDecimal.ZERO;
            BigDecimal factor = BigDecimal.ONE.subtract(percent.divide(BigDecimal.valueOf(100),4,RoundingMode.HALF_UP));
            BigDecimal total = BigDecimal.ZERO;
            if (order.getPercentOfDiscount() != null) {
                percent = order.getPercentOfDiscount();
            }
            for (OrderItem item : order.getOrderItems()){
                total.add(BigDecimal.valueOf(item.getProduct().getPrice()));
            }
            total = total.multiply(factor).setScale(2,RoundingMode.HALF_UP);
            order.setTotalCost(total);


        }
    }


}
