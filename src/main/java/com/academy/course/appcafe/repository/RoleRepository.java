package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByNameIn(List<String> names);
}
