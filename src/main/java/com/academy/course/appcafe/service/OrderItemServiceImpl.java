package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.DiscountConverter;
import com.academy.course.appcafe.converter.OrderItemConverter;
import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.model.*;
import com.academy.course.appcafe.repository.DiscountRepository;
import com.academy.course.appcafe.repository.OrderItemRepository;
import com.academy.course.appcafe.repository.OrderRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService{

    private final OrderItemRepository orderItemRepository;
    private final OrderItemConverter orderItemConverter;
    private final DiscountRepository discountRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderItemDTO getOrderItemById(Long orderItemId) throws SQLException {
        if (orderItemRepository.existsById(orderItemId)) {
            return orderItemConverter.toOrderItemDTO(orderItemRepository.getReferenceById(orderItemId));
        }
        return null;
     }

    @Override
    public void deleteItem(Long orderItemId) throws SQLException {
        if (orderItemRepository.existsById(orderItemId)) {
            orderRepository.deleteById(orderItemId);
        }
    }

    @Override
    public Page<OrderItemDTO> getPaginatedItems(int page,int size) {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItem> orderItemPage = orderItemRepository.findAll(pageable);
        List<OrderItemDTO> orderItemDTOS = orderItemPage.getContent().stream()
                .map(orderItemConverter::toOrderItemDTO)
                .toList();

        return new PageImpl<>(orderItemDTOS, pageable, orderItemPage.getTotalElements());
    }

    @Override
    public Page<OrderItemDTO> getAllItemsFromOrder(int page,int size,Long orderId) throws SQLException {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Order order = orderRepository.getReferenceById(orderId);
        Page<OrderItem> orderItemsPage = orderItemRepository.findAllByOrderIs(order,pageable);
        List<OrderItemDTO> orderItemDTOS = orderItemsPage.getContent().stream()
                .map(orderItemConverter::toOrderItemDTO)
                .toList();

        return new PageImpl<>(orderItemDTOS,pageable,orderItemsPage.getTotalElements());
    }

    @Override
    public void countAmountOfItem(Long itemId, Long discountId) throws SQLException {
        OrderItem orderItem = orderItemRepository.findById(itemId).orElse(null);

        BigDecimal percent = BigDecimal.ZERO;

        orderItem.setPriceBeforeDiscount(BigDecimal.valueOf(orderItem.getProduct().getPrice()));

        if (discountId != null) {
            Optional<Discount> discountOptional = discountRepository.findById(discountId);
            if (discountOptional.isPresent()) {
                Discount discount = discountOptional.get();
                orderItem.setAppliedDiscount(discount);
                if (discount.getPercentOfDiscount() != null) {
                    percent = discount.getPercentOfDiscount();
                }
            }
        }

        orderItem.setAppliedPercent(percent);

            BigDecimal factor = BigDecimal.ONE
                    .subtract(percent.divide(BigDecimal.valueOf(100),4, RoundingMode.HALF_UP));

            BigDecimal total = orderItem.getPriceBeforeDiscount()
                    .multiply(factor)
                    .multiply(BigDecimal.valueOf(orderItem.getProductQuantity()))
                    .setScale(2,RoundingMode.HALF_UP);

            orderItem.setTotalPrice(total);

            orderItemRepository.save(orderItem);
        }


//        logger.info("Successful setting up discount on item with id {}",itemId);
}
