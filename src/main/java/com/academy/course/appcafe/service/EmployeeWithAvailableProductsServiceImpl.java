package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.EmployeeConverter;
import com.academy.course.appcafe.converter.ProductConverter;
import com.academy.course.appcafe.dto.EmployeeWithAvailableProducts;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.model.Employee;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.repository.OrderRepository;
import com.academy.course.appcafe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeWithAvailableProductsServiceImpl implements EmployeeWithOrderAndAvailableProductsService {

    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderService orderService;
    private final EmployeeConverter employeeConverter;
    private final ProductConverter productConverter;
    @Override
    public EmployeeWithAvailableProducts getPairEntitiesByEmployeeLogin(String login) {
        if (employeeRepository.existsByLogin(login)) {
            List<ProductDTO> productDTOList = productRepository.findAllByIsAvailable(true).stream()
                    .map(productConverter::toProductDto)
                    .toList();
            return EmployeeWithAvailableProducts.builder()
                    .orderDTO(orderService.addNewOrderToEmployeeByLogin(login))
                    .productDTOList(productDTOList)
                    .build();
        }
        return null;
    }
}
