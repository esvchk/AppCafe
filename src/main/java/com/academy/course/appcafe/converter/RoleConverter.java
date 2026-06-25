package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<Role, RoleDTO> {
    @Override
    public RoleDTO convert(Role source) {
        return RoleDTO.builder()
                .id(source.getId())
                .name(source.getName())
//                .employeeDTOS()
//                .discountDTOS()
                .build();
    }
}
