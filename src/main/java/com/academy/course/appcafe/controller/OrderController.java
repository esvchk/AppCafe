package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
}
