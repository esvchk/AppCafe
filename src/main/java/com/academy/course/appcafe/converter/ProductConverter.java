package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.model.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductConverter{

    @Mapping(source = "category",target = "categoryDTO")
    ProductDTO toProductDto(Product product);

    @Mapping(source = "categoryDTO",target = "category")
    Product toEntityProduct(ProductDTO productDTO);


}
