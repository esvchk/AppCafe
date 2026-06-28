package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;
}
