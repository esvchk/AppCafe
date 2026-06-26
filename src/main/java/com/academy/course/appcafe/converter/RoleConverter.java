package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.model.Role;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = DiscountConverter.class)
public interface RoleConverter {

    @Mapping(source = "discount",target = "discountDTO")
    RoleDTO toRoleDTO(Role role);

    @Mapping(source = "discountDTO",target = "discount")
    Role toRoleEntity(RoleDTO roleDTO);
}
