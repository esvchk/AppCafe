package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.OrderOfEmployeeWithAvailableProducts;


public interface OrderOfEmployeeWithAvailableProductsService {
    OrderOfEmployeeWithAvailableProducts getPairEntitiesByEmployeeLogin(String login, Long orderId, int page, int size);
}
