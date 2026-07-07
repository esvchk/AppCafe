package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.ProductConverter;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.exception.EmptyListException;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.exception.EntityNotFoundByNameException;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAvailableProducts(int page, int size) {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> availableProductsPage = productRepository.findAllByIsAvailableTrue(true, pageable);
        List<ProductDTO> productDTOS = availableProductsPage.getContent().stream()
                .map(productConverter::toProductDto)
                .toList();

        return new PageImpl<>(productDTOS, pageable, availableProductsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getPaginatedListOfProducts(int offset, int size) {
        if (offset < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(offset, size);
        Page<Product> page = productRepository.findAll(pageable);
        List<ProductDTO> productDTOS = page.getContent().stream()
                .map(productConverter::toProductDto)
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOS, pageable, page.getTotalElements());
    }

    @Override
    public void setProductLimit(Long id, Integer limit) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(id));
        if (limit == null) {
            product.setProductLimit(null);
            if (!product.getIsAvailable()) {
                product.setIsAvailable(true);
            }
        } else {
            product.setProductLimit(limit);
            product.setIsAvailable(!product.getProductLimit().equals(0));
        }
        productRepository.save(product);

//        logger.info("Product limit has been successfully installed");
    }

    @Override
    public void updateProduct(Long oldValueId, ProductDTO newValue) {
        Product oldProduct = productRepository.findById(oldValueId).orElseThrow(() -> new EntityNotFoundByIdException(oldValueId));
            oldProduct.setName(newValue.getName());
            oldProduct.setPrice(newValue.getPrice());
            oldProduct.setInfo(newValue.getInfo());
            oldProduct.setIsAvailable(newValue.getIsAvailable());
            productRepository.save(productConverter.toEntityProduct(newValue));

    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        productRepository.save(productConverter.toEntityProduct(productDTO));
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
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(id));
        return productConverter.toProductDto(product);
    }

    @Override
    public List<ProductDTO> findProductsByName(String name) {
        List<ProductDTO> products = productRepository.findAllByName(name).stream()
                .map(productConverter::toProductDto)
                .toList();
        if (products == null || products.isEmpty()) {
            throw new EntityNotFoundByNameException(name);
        }
        return products;

    }



    @Override
    public void setIsAvailableToProduct(Long productId, Boolean isAvailable) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundByIdException(productId));
        product.setIsAvailable(isAvailable);
        productRepository.save(product);
    }
}
