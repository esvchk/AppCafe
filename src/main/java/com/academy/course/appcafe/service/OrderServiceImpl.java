package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.OrderConverter;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.exception.EntityNotFoundByNameException;
import com.academy.course.appcafe.exception.WrongValueException;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.model.OrderItem;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.OrderItemRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import com.academy.course.appcafe.repository.ProductRepository;
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


    @Override
    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long orderId) {
        return orderConverter.toOrderDTO(orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundByIdException(orderId)));


    }

    @Override
    public OrderDTO addNewOrderToEmployeeByLogin(String login) {
            Employee employee = employeeRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundByNameException(login));
            Order order = Order.builder()
                    .employee(employee)
                    .isBought(false)
                    .build();
            employee.addOrder(order);

            orderRepository.save(order);
            return orderConverter.toOrderDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderConverter::toOrderDTO)
                .toList();
    }


    @Override
    public void buyOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundByIdException(orderId));
        countAmountOfOrder(orderId);
        order.setIsBought(true);
        orderRepository.save(order);

    }


    @Override
    public void addProductToOrder(Long productId, Long orderId, Integer quantity) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundByIdException(orderId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundByIdException(productId));
        Optional<OrderItem> items = order.getOrderItems().stream()
                .filter(item1 -> item1.getProduct().getId().equals(product.getId()))
                .findFirst();
        if (items.isPresent()) {
            items.get().setProductQuantity(items.get().getProductQuantity() + quantity);
        } else {
            OrderItem item = OrderItem.builder()
                    .productQuantity(quantity)
                    .product(product)
                    .order(order)
                    .build();
            order.addOrderItem(item);

        }

        if (product.getProductLimit() != null) {
            if (product.getProductLimit() >= 1) {
                productService.setProductLimit(product.getId(), product.getProductLimit() - quantity);

            } else {
                throw new WrongValueException("Quantity cannot be bigger than limit", String.valueOf(product.getProductLimit()));
            }
        }
        countAmountOfOrder(orderId);
        orderRepository.save(order);

    }


    @Override
    public void deleteItemFromOrder(Long itemId, Long orderId, Integer quantity) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundByIdException(orderId));
        OrderItem item = orderItemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundByIdException(itemId));
        if (item.getProductQuantity().equals(quantity)) {
            order.getOrderItems().remove(item);
        } else {
            item.setProductQuantity(item.getProductQuantity() - quantity);
        }

        order.setTotalCost(countAmountOfOrder(orderId));


        orderRepository.save(order);

    }

    @Override
    public BigDecimal countTotalAmountOfOrders() {
        return orderRepository.getTotalOrdersAmount();
    }

    @Override
    public BigDecimal countAmountOfOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundByIdException(orderId));
        BigDecimal percent = BigDecimal.ZERO;
        BigDecimal factor = BigDecimal.ONE.subtract(percent.divide(BigDecimal.valueOf(100), 4, RoundingMode.UP));
        BigDecimal total = BigDecimal.ZERO;

        if (order.getPercentOfDiscount() != null) {
            percent = order.getPercentOfDiscount();
        }

        for (OrderItem item : order.getOrderItems()) {

            BigDecimal price = BigDecimal.valueOf(item.getProduct().getPrice());
            Integer quantity = item.getProductQuantity();
            if (quantity == null) quantity = 1;
            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
        }

        total = total.multiply(factor).setScale(4, RoundingMode.UP);
        order.setTotalCost(total);


        orderRepository.save(order);
        return total;
    }

    @Override
    public void setDiscountOnOrder(Long orderId, BigDecimal percent) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundByIdException(orderId));
        order.setPercentOfDiscount(percent);
        orderRepository.save(order);
    }

    @Override
    public void inputPaymentDataToOrder(String paymentData, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundByIdException(orderId));
        order.setPaymentData(paymentData);
        orderRepository.save(order);
    }


}
