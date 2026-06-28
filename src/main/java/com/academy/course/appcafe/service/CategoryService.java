package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void createCategory(CategoryDTO categoryDTO) throws SQLException;
    void addProductToCategory(Long categoryId, Long productId) throws SQLException;
    void updateCategory(Long oldValueId,CategoryDTO newValue) throws SQLException;
    void deleteCategory(Long categoryId) throws SQLException;
    CategoryDTO getCategoryById(Long id) throws SQLException;
    Page<CategoryDTO> getPaginatedCategories(int page,int size);
}
