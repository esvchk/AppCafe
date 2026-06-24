package com.academy.course.appcafe.repository;

import com.academy.course.appcafe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
