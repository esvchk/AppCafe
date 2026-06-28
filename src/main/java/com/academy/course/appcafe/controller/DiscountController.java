package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeRequest;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;


    @RequestMapping(value = "/getDiscountPage", method = RequestMethod.GET)
    public String paginatedEmployees(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "5") int size, Model model) {

        Page<DiscountDTO> discountPage = discountService.getPaginatedListOfDiscounts(page, size);

        model.addAttribute("discountPage",discountPage);
        model.addAttribute("page",page);
        model.addAttribute("size", size);
        model.addAttribute("discount",new DiscountDTO());
        return "discount-pages";
    }

    @RequestMapping(value = "/addDiscount", method = RequestMethod.POST)
    public String addDiscount(Model model, DiscountDTO discountDTO) throws SQLException {
        discountService.createNewDiscount(discountDTO);
        model.addAttribute("newDiscount", new DiscountDTO());
        return "redirect:/getDiscountPage";
    }
}
