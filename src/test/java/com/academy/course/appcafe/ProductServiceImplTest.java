package com.academy.course.appcafe;

import com.academy.course.appcafe.converter.ProductConverter;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.ProductRepository;
import com.academy.course.appcafe.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    private Product product;
    private ProductDTO productDTO;

    @Test
    void contextLoads() {
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(99.99);
        product.setIsAvailable(true);
        product.setProductLimit(10);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Updated Product");
        productDTO.setPrice(149.99);
        productDTO.setIsAvailable(false);
        productDTO.setProductLimit(5);
    }

    @Test
    void getAvailableProducts_returnsPageWithCorrectData() {
        int page = 0;
        int size = 10;

        Page<Product> pageResult = new PageImpl<>(List.of(product), PageRequest.of(page, size), 1);
        when(productRepository.findAllByIsAvailableTrue(eq(true), any(Pageable.class)))
                .thenReturn(pageResult);
        when(productConverter.toProductDto(any(Product.class))).thenReturn(productDTO);

        Page<ProductDTO> result = productService.getAvailableProducts(page, size);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);
        verify(productRepository).findAllByIsAvailableTrue(eq(true), any(Pageable.class));
    }
}
