package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.CategoryConverter;
import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.OrderItemDTO;
import com.academy.course.appcafe.model.Category;
import com.academy.course.appcafe.model.Order;
import com.academy.course.appcafe.model.OrderItem;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.CategoryRepository;
import com.academy.course.appcafe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final ProductRepository productRepository;


    @Override
    public void createCategory(CategoryDTO categoryDTO) throws SQLException {
        categoryRepository.save(categoryConverter.toCategoryEntity(categoryDTO));
    }

    @Override
    public void addProductToCategory(Long categoryId, Long productId) throws SQLException {
        Product product = productRepository.findById(productId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        category.addProduct(product);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long oldValueId, CategoryDTO newValue) throws SQLException {
        if (categoryRepository.existsById(oldValueId)) {
            Category category = categoryRepository.getReferenceById(oldValueId);
            category.setName(newValue.getName());
            categoryRepository.save(category);
        }
    }

    @Override
    public void deleteCategory(Long categoryId) throws SQLException {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        }
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) throws SQLException {
        if (categoryRepository.existsById(categoryId)) {
            return categoryConverter.toCategoryDTO(categoryRepository.getReferenceById(categoryId));
        }
        return null;
    }

    @Override
    public Page<CategoryDTO> getPaginatedCategories(int page,int size) {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryDTO> categoryDTOS =categoryPage.getContent().stream()
                .map(categoryConverter::toCategoryDTO)
                .toList();

        return new PageImpl<>(categoryDTOS,pageable,categoryPage.getTotalElements());
    }

    @Override
    public void removeProductFromCategory(Long categoryId, Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        category.removeProduct(product);
        categoryRepository.save(category);

    }

}
