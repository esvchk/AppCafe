package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.RoleConverter;
import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;


    @Override
    public List<RoleDTO> findRolesByName(List<String> names) {
        return roleRepository.findAllByNameIn(names).stream()
                .map(roleConverter::toRoleDTO)
                .toList();
    }
}
