package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.DiscountConverter;
import com.academy.course.appcafe.converter.OrderItemConverter;
import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.model.OrderItem;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.OrderItemRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{

    private final OrderItemRepository orderItemRepository;
    private final OrderItemConverter orderItemConverter;
    private final DiscountConverter discountConverter;
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
    public Page<OrderItemDTO> getAllItems() {
        return null;
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
    public void setDiscountOnItem(Long itemId, DiscountDTO discount) throws SQLException {
        if (orderItemRepository.existsById(itemId)) {
            OrderItem item = orderItemRepository.getReferenceById(itemId);
            orderItemRepository.save(item);
        }

//        logger.info("Successful setting up discount on item with id {}",itemId);
    }
}
