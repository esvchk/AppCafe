package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.ProductConverter;
import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.exception.EmptyEntityException;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAvailableProducts(int page,int size) {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> availableProductsPage = productRepository.findAllByIsAvailableTrue(true,pageable);
        List<ProductDTO> productDTOS = availableProductsPage.getContent().stream()
                .map(productConverter::toProductDto)
                .toList();

        return new PageImpl<>(productDTOS,pageable,availableProductsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getPaginatedListOfProducts(int offset, int size) {
        if (offset < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(offset,size);
        Page<Product> page = productRepository.findAll(pageable);
        List<ProductDTO> productDTOS = page.getContent().stream()
                .map(productConverter::toProductDto)
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOS,pageable,page.getTotalElements());
    }

    @Override
    public void setProductLimit(Long id, Integer limit){
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(id));
            if (limit == null) {
                product.setProductLimit(null);
            } else {
                product.setProductLimit(limit);
                product.setIsAvailable(!product.getProductLimit().equals(0));
            }
            productRepository.save(product);

//        logger.info("Product limit has been successfully installed");
    }

    @Override
    public void updateProduct(Long oldValueId,ProductDTO newValue) {
        Product oldProduct = productRepository.findById(oldValueId).orElseThrow(() -> new EntityNotFoundByIdException(oldValueId));
        if (newValue != null) {
            oldProduct.setName(newValue.getName());
            oldProduct.setPrice(newValue.getPrice());
            oldProduct.setInfo(newValue.getInfo());
            oldProduct.setIsAvailable(newValue.getIsAvailable());
            oldProduct.setProductLimit(newValue.getProductLimit());
            productRepository.save(productConverter.toEntityProduct(newValue));
        } else {
            throw new EmptyEntityException(newValue);
        }
//              logger.info("Product with id {} has been successfully updated", oldValueId);

    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        if (productDTO != null) {
            productRepository.save(productConverter.toEntityProduct(productDTO));
        } else {
            throw new EmptyEntityException(productDTO);
        }

//        logger.info("New product {} has been successfully added", productDTO);
    }

    @Override
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundByIdException(id);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        if (productRepository.existsById(id)) {
            return productConverter.toProductDto(productRepository.getReferenceById(id));
        } else {
            throw new EntityNotFoundByIdException(id);
        }

    }

    @Override
    public List<ProductDTO> findProductsByName(String name) {
        return productRepository.findAllByName(name).stream()
                .map(productConverter::toProductDto)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productConverter::toProductDto)
                .toList();
    }


    @Override
    public void setIsAvailableToProduct(Long productId,Boolean isAvailable) {
        Product product = productRepository.findById(productId).orElse(null);
        product.setIsAvailable(isAvailable);
        productRepository.save(product);
    }
}
