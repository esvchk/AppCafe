package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.OrderConverter;
import com.academy.course.appcafe.converter.ProductConverter;
import com.academy.course.appcafe.dto.EmployeeWithAvailableProducts;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import com.academy.course.appcafe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeWithAvailableProductsServiceImpl implements EmployeeWithOrderAndAvailableProductsService {

    private final ProductService productService;
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final ProductConverter productConverter;
    private final OrderConverter orderConverter;
    @Override
    public EmployeeWithAvailableProducts getPairEntitiesByEmployeeLogin(String login,Long orderId,int page,int size) {
        if (employeeRepository.existsByLogin(login)) {
            OrderDTO orderDTO = orderConverter.toOrderDTO(orderRepository.getReferenceById(orderId));
                    Page<ProductDTO> productPage = productService.getAvailableProducts(page, size);
            return EmployeeWithAvailableProducts.builder()
                    .productDTOPage(productPage)
                    .orderDTO(orderDTO)
                    .build();
        }
        return null;
    }
}
