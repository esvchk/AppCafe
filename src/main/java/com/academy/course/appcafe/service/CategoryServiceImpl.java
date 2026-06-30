package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.CategoryConverter;
import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.exception.EmptyEntityException;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.model.Category;
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

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final ProductRepository productRepository;


    @Override
    public void createCategory(CategoryDTO categoryDTO)  {
        if (categoryDTO != null) {
            categoryRepository.save(categoryConverter.toCategoryEntity(categoryDTO));
        } else {
            throw new EmptyEntityException(categoryDTO);
        }

    }

    @Override
    public void addProductToCategory(Long categoryId, Long productId)  {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundByIdException(productId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundByIdException(categoryId));
        category.addProduct(product);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long oldValueId, CategoryDTO newValue)  {
        Category category = categoryRepository.findById(oldValueId).orElseThrow(() -> new EntityNotFoundByIdException(oldValueId));
        if (newValue != null) {
            category.setName(newValue.getName());
            categoryRepository.save(category);
        } else {
            throw new EmptyEntityException(newValue);
        }


    }

    @Override
    public void deleteCategory(Long categoryId)  {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new EntityNotFoundByIdException(categoryId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            return categoryConverter.toCategoryDTO(categoryRepository.getReferenceById(categoryId));
        } else {
            throw new EntityNotFoundByIdException(categoryId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getPaginatedCategories(int page, int size) {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryDTO> categoryDTOS = categoryPage.getContent().stream()
                .map(categoryConverter::toCategoryDTO)
                .toList();

        return new PageImpl<>(categoryDTOS, pageable, categoryPage.getTotalElements());
    }

    @Override
    public void removeProductFromCategory(Long categoryId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundByIdException(productId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundByIdException(categoryId));
        category.removeProduct(product);
        categoryRepository.save(category);

    }

}
