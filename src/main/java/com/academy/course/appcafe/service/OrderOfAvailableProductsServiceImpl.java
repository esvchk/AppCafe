package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.OrderConverter;
import com.academy.course.appcafe.dto.OrderOfEmployeeWithAvailableProducts;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderOfAvailableProductsServiceImpl implements OrderOfEmployeeWithAvailableProductsService {

    private final ProductService productService;
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    @Override
    public OrderOfEmployeeWithAvailableProducts getPairEntitiesByEmployeeLogin(String login, Long orderId, int page, int size) {
        if (employeeRepository.existsByLogin(login)) {
            OrderDTO orderDTO = orderConverter.toOrderDTO(orderRepository.getReferenceById(orderId));
                    Page<ProductDTO> productPage = productService.getAvailableProducts(page, size);
            return OrderOfEmployeeWithAvailableProducts.builder()
                    .productDTOPage(productPage)
                    .orderDTO(orderDTO)
                    .build();
        }
        return null;
    }
}
