package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryService categoryService;
}
