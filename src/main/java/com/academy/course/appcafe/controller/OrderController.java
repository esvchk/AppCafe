package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeRequest;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @RequestMapping(value = "/getOrderPage", method = RequestMethod.GET)
    public String paginatedOrders(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                     @RequestParam(value = "size", defaultValue = "5") int size, Model model) {

        Page<OrderDTO> employeePage = orderService.getPaginatedListOfOrders(offset, size);

        model.addAttribute("orderPage", employeePage);
        model.addAttribute("offset", offset);
        model.addAttribute("size", size);
        model.addAttribute("order",new OrderDTO());
        return "order-pages";
    }



}
