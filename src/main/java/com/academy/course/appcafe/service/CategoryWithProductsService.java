package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.CategoryWithProducts;

public interface CategoryWithProductsService {
    CategoryWithProducts getPairOfEntities(Long id,int page,int size);
}
