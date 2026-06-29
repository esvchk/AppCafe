package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.EmployeeWithAvailableProducts;


public interface EmployeeWithOrderAndAvailableProductsService {
    EmployeeWithAvailableProducts getPairEntitiesByEmployeeLogin(String login,Long orderId,int page,int size);
}
