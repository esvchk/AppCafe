package com.academy.course.appcafe.converter;

import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleConverter {

    RoleDTO toRoleDTO(Role role);

    Role toRoleEntity(RoleDTO roleDTO);
}
