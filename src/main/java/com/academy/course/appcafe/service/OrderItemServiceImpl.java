package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.OrderItemConverter;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.model.*;
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
    private final OrderRepository orderRepository;

    @Override
    public OrderItemDTO getOrderItemById(Long orderItemId) throws SQLException {
        return orderItemConverter.toOrderItemDTO(orderItemRepository.findById
                (orderItemId).orElseThrow(() -> new EntityNotFoundByIdException(orderItemId)));
     }

    @Override
    public void deleteItem(Long orderItemId) throws SQLException {
       OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new EntityNotFoundByIdException(orderItemId));
            orderRepository.deleteById(orderItem.getId());

    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public Page<OrderItemDTO> getAllItemsFromOrder(int page,int size,Long orderId) throws SQLException {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundByIdException(orderId));
        Page<OrderItem> orderItemsPage = orderItemRepository.findAllByOrderIs(order,pageable);
        List<OrderItemDTO> orderItemDTOS = orderItemsPage.getContent().stream()
                .map(orderItemConverter::toOrderItemDTO)
                .toList();

        return new PageImpl<>(orderItemDTOS,pageable,orderItemsPage.getTotalElements());
    }



}
