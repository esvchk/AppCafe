package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.model.Category;
import com.academy.course.appcafe.model.DataEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryConverter implements Converter<Category,CategoryDTO> {


    @Override
    public CategoryDTO convert(Category source) {
        return CategoryDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
