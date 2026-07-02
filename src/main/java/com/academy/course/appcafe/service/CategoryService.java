package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void createCategory(CategoryDTO categoryDTO) ;
    void addProductToCategory(Long categoryId, Long productId) ;
    void updateCategory(Long oldValueId,CategoryDTO newValue) ;
    void deleteCategory(Long categoryId);
    CategoryDTO getCategoryById(Long id) ;
    Page<CategoryDTO> getPaginatedCategories(int page,int size);
    void removeProductFromCategory(Long categoryId,Long productId);
}
