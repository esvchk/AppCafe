package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.CategoryConverter;
import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.CategoryWithProducts;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoryWithProductsServiceImpl implements CategoryWithProductsService{
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final CategoryConverter categoryConverter;

    @Override
    public CategoryWithProducts getPairOfEntities(Long id,int page,int size) {
        CategoryDTO categoryDTO = categoryConverter.toCategoryDTO(categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(id)));
        Page<ProductDTO> productDTOS = productService.getPaginatedListOfProducts(page, size);

        return CategoryWithProducts.builder()
                .productPage(productDTOS)
                .categoryDTO(categoryDTO)
                .build();
    }
}
