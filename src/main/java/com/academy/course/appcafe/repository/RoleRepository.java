package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
