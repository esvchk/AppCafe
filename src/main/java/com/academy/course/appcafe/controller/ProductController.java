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

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(value = "/getProductPage", method = RequestMethod.GET)
    public String paginatedProducts(@RequestParam(value = "offset", defaultValue = "1") int offset,
                                    @RequestParam(value = "size", defaultValue = "5") int size, Model model) {

        Page<ProductDTO> productPage = productService.getPaginatedListOfProducts(offset, size);

        model.addAttribute("productPage", productPage);
        model.addAttribute("offset", offset);
        model.addAttribute("size", size);
        model.addAttribute("newProduct", new ProductDTO());
        return "product-pages";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(Model model, ProductDTO productDTO) throws SQLException {
        productService.addProduct(productDTO);
        model.addAttribute("newProduct", new ProductDTO());
        return "redirect:/getProductPage";
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") Long id) throws SQLException {
        productService.deleteProduct(id);
        return "redirect:/getProductPage";
    }

    @RequestMapping(value = "/editProduct", method = RequestMethod.GET)
    public String showUpdateFormProduct(@RequestParam("id") Long oldValueId, Model model) throws SQLException {
        model.addAttribute("product", productService.getProductById(oldValueId));
        return "editProduct-form";
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public String updateProduct(ProductDTO newValue) throws SQLException {
        productService.updateProduct(newValue.getId(), newValue);
        return "redirect:/getProductPage";
    }

    @RequestMapping(value = "/findByName",method = RequestMethod.GET)
    public String findByName(@RequestParam("name")String name,
                             Model model){
        model.addAttribute("productsByName",productService.findProductsByName(name));
        model.addAttribute("name",name);
        return "productByName-results";
    }
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public String findById(@RequestParam("id")Long id,
                           Model model) throws SQLException {
        model.addAttribute("productById",productService.getProductById(id));
        return "productById-results";
    }
}
