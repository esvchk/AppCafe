package com.academy.course.appcafe.service.service;

import com.academy.course.appcafe.converter.ProductConverter;
import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.exception.EntityNotFoundByIdException;
import com.academy.course.appcafe.exception.EntityNotFoundByNameException;
import com.academy.course.appcafe.model.Product;
import com.academy.course.appcafe.repository.ProductRepository;
import com.academy.course.appcafe.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductConverter productConverter;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void addProductTest(){
        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .name("Product")
                .price(1.5)
                .info("info")
                .productLimit(2)
                .isAvailable(true)
                .categoryDTO(new CategoryDTO())
                .build();

        Product product = new Product();
        when(productConverter.toEntityProduct(productDTO)).thenReturn(product);

        productService.addProduct(productDTO);

        verify(productConverter,times(1)).toEntityProduct(productDTO);
        verify(productRepository,times(1)).save(product);
    }

    @Test
    void getProductByIdTest(){
        Long id = 1L;
        ProductDTO productDTO = ProductDTO.builder()
                .name("Product")
                .price(1.5)
                .info("info")
                .productLimit(2)
                .isAvailable(true)
                .categoryDTO(new CategoryDTO())
                .build();

        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productConverter.toProductDto(any())).thenReturn(productDTO);
        ProductDTO returnedProduct = productService.getProductById(id);
        assertNotNull(returnedProduct);
        assertEquals(productDTO.getName(),returnedProduct.getName());
        verify(productRepository,times(1)).findById(id);
    }

    @Test
    void getProductByIdThrowsException(){
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundByIdException.class,() -> {
            productService.getProductById(id);
        });

        verify(productRepository,times(1)).findById(id);
        verifyNoInteractions(productConverter);


    }
    @Test
    void getProductsByNameTest(){

        String name = "name";

        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .name("name")
                .price(1.5)
                .info("info")
                .productLimit(2)
                .isAvailable(true)
                .categoryDTO(new CategoryDTO())
                .build();

        Product product = new Product();
        List<Product> productsFromDb = List.of(product);


        when(productRepository.findAllByName(name)).thenReturn(productsFromDb);
        when(productConverter.toProductDto(any())).thenReturn(productDTO);

        List<ProductDTO> actualList = productService.findProductsByName(name);

        assertNotNull(actualList);
        assertEquals(1, actualList.size());
        assertNotNull(actualList.get(0).getName(), "Имя в DTO вернулось null!");
        assertEquals(name, actualList.get(0).getName());

        verify(productRepository, times(1)).findAllByName(name);
        verify(productConverter, times(1)).toProductDto(any());

    }

    @Test
    void findProductsByNameThrowsExceptionTest(){
        String searchName = "Unknown Product";
        when(productRepository.findAllByName(searchName)).thenReturn(Collections.emptyList());


        EntityNotFoundByNameException exception = assertThrows(EntityNotFoundByNameException.class, () -> {
            productService.findProductsByName(searchName);
        });

        assertTrue(exception.getMessage().contains(searchName));

        verify(productRepository, times(1)).findAllByName(searchName);
        verifyNoInteractions(productConverter);

    }

    @Test
    void deleteProductByIdTest(){
        Long id = 1L;

        when(productRepository.existsById(id)).thenReturn(true);
        productService.deleteProduct(id);

        verify(productRepository,times(1)).existsById(id);
        verify(productRepository,times(1)).deleteById(id);
    }

    @Test
    void deleteProductThrowsExceptionTest(){

        Long id =  2L;

        when(productRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundByIdException.class, () -> {
            productService.deleteProduct(id);
        });

        verify(productRepository,times(1)).existsById(id);
        verify(productRepository, never()).deleteById(id);
    }

    @Test
    void updateProductTest(){

        Long id = 1L;

        ProductDTO newValue = ProductDTO.builder()
                .name("newName")
                .price(1.2)
                .info("newInfo")
                .productLimit(3)
                .isAvailable(true)
                .build();

        Product oldValue = new Product();
        oldValue.setId(id);
        oldValue.setName(newValue.getName());
        oldValue.setInfo(newValue.getInfo());
        oldValue.setPrice(newValue.getPrice());
        oldValue.setProductLimit(newValue.getProductLimit());
        oldValue.setIsAvailable(newValue.getIsAvailable());

        when(productRepository.findById(id)).thenReturn(Optional.of(oldValue));

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        productService.updateProduct(id,newValue);

        verify(productRepository,times(1)).findById(id);
        verify(productRepository,times(1)).save(productArgumentCaptor.capture());

        Product savedProduct = productArgumentCaptor.getValue();

        assertNotNull(savedProduct);
        assertEquals(id, savedProduct.getId());
        assertEquals("newName", savedProduct.getName());
        assertEquals(1.2, savedProduct.getPrice());
        assertEquals("newInfo", savedProduct.getInfo());
        assertTrue(savedProduct.getIsAvailable());

    }

    @Test
    void updateProductThrowsExceptionTest(){
        Long oldValueId = 999L;
        ProductDTO newValueDto = new ProductDTO();

        when(productRepository.findById(oldValueId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundByIdException.class, () -> {
            productService.updateProduct(oldValueId, newValueDto);
        });

        verify(productRepository, times(1)).findById(oldValueId);
        verify(productRepository, never()).save(any());
        verifyNoInteractions(productConverter);


    }


}
