package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.annotation.ValidId;
import com.academy.course.appcafe.annotation.ValidPagination;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.service.ProductService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ValidPagination
    @RequestMapping(value = "/getProductPage", method = RequestMethod.GET)
    public String paginatedProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "5") int size, Model model) {

        Page<ProductDTO> productPage = productService.getPaginatedListOfProducts(page, size);

        model.addAttribute("productPage", productPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("newProduct", new ProductDTO());
        return "product-pages";
    }

    @PostMapping(value = "/addProduct")
    public String addProduct(@Valid @ModelAttribute(name = "newProduct") ProductDTO productDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "add-product";
        }
        productService.addProduct(productDTO);

        return "redirect:/getProductPage";
    }

    @GetMapping(value = "/addingProductForm")
    public String showProductForm(Model model) {
        model.addAttribute("newProduct", new ProductDTO());
        return "add-product";
    }


    @ValidId
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/getProductPage";
    }


    @ValidId
    @RequestMapping(value = "/editProduct", method = RequestMethod.GET)
    public String showUpdateFormProduct(@RequestParam("id") Long oldValueId, Model model) {
        model.addAttribute("product", productService.getProductById(oldValueId));
        return "editProduct-form";
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public String updateProduct(@Valid @ModelAttribute(name = "product") ProductDTO newValue, BindingResult result) {
        if (result.hasErrors()) {
            return "editProduct-form";
        }
        productService.updateProduct(newValue.getId(), newValue);
        return "redirect:/getProductPage";
    }

    @RequestMapping(value = "/findProductByName", method = RequestMethod.GET)
    public String findByName(@RequestParam(name = "name")
                             @Size(min = 2, max = 20, message = "Name must have size from 2 to 20 symbols")
                             @Pattern(regexp = "(^[A-Z])([a-z\\s,-]*)",
                                     message = "Name must starts with upper case letter and without figures")
                             String name,
                             Model model) {
        model.addAttribute("productsByName", productService.findProductsByName(name));
        model.addAttribute("name", name);
        return "productByName-results";
    }

    @ValidId
    @RequestMapping(value = "/findProductById", method = RequestMethod.GET)
    public String findById(@RequestParam("id") Long id,
                           Model model) {
        model.addAttribute("productById", productService.getProductById(id));
        return "productById";
    }

    @ValidId
    @RequestMapping(value = "/setProductLimit", method = RequestMethod.POST)
    public String setProductLimit(@RequestParam("id") Long id,
                                  @RequestParam(name = "productLimit", required = false)
                                  @Nullable
                                  @Min(value = 0, message = "min limit 0")
                                  @Max(value = 100, message = "max limit 100")
                                  Integer limit) {

        productService.setProductLimit(id, limit);
        return "redirect:/getProductPage";
    }

    @ValidId
    @RequestMapping(value = "/editLimit", method = RequestMethod.GET)
    public String showLimitForm(@RequestParam("id") Long id,
                                Model model) {
        model.addAttribute("productWithLimit", productService.getProductById(id));
        return "limit-form";
    }

    @ValidId
    @RequestMapping(value = "/setIsAvailable", method = RequestMethod.POST)
    public String setIsAvailable(@RequestParam("id") Long id,
                                 @RequestParam(name = "isAvailable", required = true) Boolean isAvailable) {
        productService.setIsAvailableToProduct(id, isAvailable);
        return "redirect:/getProductPage";
    }

}
