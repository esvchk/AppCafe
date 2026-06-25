package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Override
    public Set<ProductDTO> getAvailableProducts() {
        return Set.of();
    }

    @Override
    public Page<ProductDTO> getPaginatedListOfProducts(int offset, int size) {
        if (offset < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(offset,size);
        Page<Product> page = productRepository.findAll(pageable);
        List<ProductDTO> productDTOS = page.getContent().stream()
                .map(product -> conversionService.convert(product, ProductDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOS,pageable,page.getTotalElements());
    }

    @Override
    public void setProductLimit(Integer id, Integer limit) throws SQLException {

    }

    @Override
    public void updateProduct(Integer oldValueId, ProductDTO newValue) throws SQLException {

    }

    @Override
    public void addProduct(ProductDTO productDTO) throws SQLException {

    }

    @Override
    public void deleteProduct(Integer id) throws SQLException {

    }

    @Override
    public ProductDTO getProductById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Set<ProductDTO> findProductsByName(String name) {
        return Set.of();
    }

    @Override
    public Set<ProductDTO> getAllProducts() {
        return Set.of();
    }

    @Override
    public Set<ProductDTO> getAllProductsFromCategory(CategoryDTO categoryDTO) throws SQLException {
        return Set.of();
    }
}
