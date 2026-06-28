package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.OrderConverter;
import com.academy.course.appcafe.converter.OrderItemConverter;
import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.model.*;
import com.academy.course.appcafe.repository.DiscountRepository;
import com.academy.course.appcafe.repository.OrderItemRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import com.academy.course.appcafe.repository.ProductRepository;
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
import java.util.Optional;
import java.util.Set;
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderItemRepository orderItemRepository;
    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;


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
        if (orderRepository.existsById(orderId)) {
            Order order = orderRepository.getReferenceById(orderId);
            order.setIsBought(true);
            countAmountOfOrder(orderId,order.getOrderDiscount().getId());
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

        Order order = orderRepository.getReferenceById(orderId);
        Product product = productRepository.getReferenceById(productId);
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

        if (product.getProductLimit() != null)
            productService.setProductLimit(product.getId(),
                    product.getProductLimit() - quantity);



        orderRepository.save(order);

//        logger.info("Product {} has been successfully added to order {} with quantity {} "
//                , product, order, quantity);

    }

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
