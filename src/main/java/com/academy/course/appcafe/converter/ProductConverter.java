package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<Product, ProductDTO> {
    @Override
    public ProductDTO convert(Product source) {
        return ProductDTO.builder()
                .id(source.getId())
                .name(source.getName())
//                .price(source.getPrice())
                .info(source.getInfo())
                .productLimit(source.getProductLimit())
                .isAvailable(source.getIsAvailable())
//                .categoryDTO(source.getCategory())
                .build();
    }
}
