package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.repository.ProductRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface ProductService {

    Page<ProductDTO> getAvailableProducts(int page,int size);
    Page<ProductDTO> getPaginatedListOfProducts(int page, int size);
    void setProductLimit(Long id,Integer limit) throws SQLException;
    void updateProduct(Long oldValueId,ProductDTO newValue) throws SQLException;
    void addProduct(ProductDTO productDTO) throws SQLException;
    void deleteProduct(Long id) throws SQLException;
    ProductDTO getProductById(Long id) throws SQLException;
    List<ProductDTO> findProductsByName(String name);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProductsFromCategory(CategoryDTO categoryDTO) throws SQLException;

}
