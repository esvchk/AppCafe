package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(value = "/getProductPage",method = RequestMethod.GET)
    public String paginatedProducts(@RequestParam(value = "offset",defaultValue = "1")int offset,
                                    @RequestParam(value = "size",defaultValue = "5")int size, Model model){

        Page<ProductDTO> productPage = productService.getPaginatedListOfProducts(offset, size);

        model.addAttribute("productPage", productPage);
        model.addAttribute("offset", offset);
        model.addAttribute("size", size);
        model.addAttribute("newProduct",new ProductDTO());
        return "product-pages";
    }
}
