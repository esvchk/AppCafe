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
    private final DiscountRepository discountRepository;
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
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new NullPointerException());
            order.setIsBought(true);

            Long discountId = (order.getOrderDiscount() != null) ? order.getOrderDiscount().getId() : null;

            countAmountOfOrder(orderId, discountId);
            orderRepository.save(order);
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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден: " + orderId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден: " + productId));

        // Проверка лимита ДО любых изменений
        Integer limit = product.getProductLimit();
        if (limit != null && limit < quantity) {
            throw new IllegalStateException("Недостаточно товара: требуется " + quantity + ", доступно " + limit);
        }

        // Поиск существующей позиции
        Optional<OrderItem> existingItemOpt = order.getOrderItems().stream()
                .filter(item -> item.getProduct() != null
                        && item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            OrderItem item = existingItemOpt.get();
            item.setProductQuantity(item.getProductQuantity() + quantity);
        } else {
            OrderItem item = OrderItem.builder()
                    .productQuantity(quantity)
                    .product(product)
                    .order(order)
                    .build();
            order.addOrderItem(item);
        }

        // Обновляем лимит товара
        if (limit != null) {
            productService.setProductLimit(product.getId(), limit - quantity);
        }

        // Пересчитываем только изменённую позицию (или вообще вынести пересчёт на момент покупки)
        // Если нужно пересчитать все — лучше сделать один вызов на уровне заказа, а не по каждой позиции
        OrderItem updatedItem = existingItemOpt.orElseGet(() -> order.getOrderItems().stream()
                        .filter(i -> i.getProduct() != null
                                && i.getProduct().getId().equals(product.getId()))
                        .findFirst()
                        .orElse(null));

        if (updatedItem != null) {

            Long discountId = (updatedItem.getAppliedDiscount() != null)
                    ? updatedItem.getAppliedDiscount().getId()
                    : null;
            orderItemService.countAmountOfItem(updatedItem.getId(), discountId);
        }

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
    public void countAmountOfOrder(Long orderId, Long discountId) throws SQLException {
        Order order = orderRepository.findWithDiscountAndItems(orderId).orElse(null);

        BigDecimal percent = BigDecimal.ZERO;
        if (discountId != null) {
            Optional<Discount> discountOptional = discountRepository.findById(discountId);
            if (discountOptional.isPresent()) {
                Discount discount = discountOptional.get();
                if (discount.getPercentOfDiscount() != null) {
                    percent = discount.getPercentOfDiscount();
                }
            }
        }
        order.setPercentOfDiscount(percent);

        BigDecimal subTotal = order.getOrderItems().stream()
                .map(OrderItem::getTotalPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal factor = BigDecimal.ONE
                .subtract(percent.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));

        BigDecimal total = subTotal
                .multiply(factor)
                .setScale(2, RoundingMode.HALF_UP);

        order.setTotalCost(total);
        orderRepository.save(order);
    }


}
