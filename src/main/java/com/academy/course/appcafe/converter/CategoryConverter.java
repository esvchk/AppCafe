package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.type.TypeFactory;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryConverter implements Converter<Category,CategoryDTO> {

    private final ProductConverter productConverter;

    @Override
    public CategoryDTO convert(Category source) {
        return CategoryDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .productsDTO(source.getProducts().stream()
                        .map(productConverter::convert)
                        .collect(Collectors.toSet()))
                .build();
    }
}
