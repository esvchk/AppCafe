package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.model.Category;
import com.academy.course.appcafe.model.DataEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = ProductConverter.class)
public interface CategoryConverter {

    @Mapping(source = "products",target = "productDTOS")
    CategoryDTO toCategoryDTO(Category category);

    @Mapping(source = "productDTOS",target = "products")
    Category toCategoryEntity(CategoryDTO categoryDTO);

}
