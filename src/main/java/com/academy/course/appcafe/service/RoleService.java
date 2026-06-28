package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findRolesByName(List<String> names);
    List<RoleDTO> findAll();
}
