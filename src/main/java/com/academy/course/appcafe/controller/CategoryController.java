package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.service.CategoryService;
import com.academy.course.appcafe.service.CategoryWithProductsService;
import com.academy.course.appcafe.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryWithProductsService categoryWithProductsService;
    private final ProductService productService;

    @RequestMapping(value = "/getCategoryPage", method = RequestMethod.GET)
    public String paginatedCategories(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "size", defaultValue = "5") int size, Model model) {

        Page<CategoryDTO> categoryPage = categoryService.getPaginatedCategories(offset, size);

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("offset", offset);
        model.addAttribute("size", size);
        model.addAttribute("category", new CategoryDTO());
        return "category-pages";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(Model model, @Valid CategoryDTO categoryDTO) throws SQLException {
        categoryService.createCategory(categoryDTO);
        model.addAttribute("category", new CategoryDTO());
        return "redirect:/getCategoryPage";
    }

    @PostMapping(value = "/addProductInCategory")
    public String addProductInCategory(@RequestParam("categoryId") Long categoryId,
                                       @RequestParam("productId") Long productId) throws SQLException {
        categoryService.addProductToCategory(categoryId, productId);
        return "redirect:/showCategoryPage/" + categoryId;
    }

    @GetMapping(value = "/showCategoryPage/{categoryId}")
    public String categoryPage(Model model,
                               @PathVariable Long categoryId,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("categoryWithProducts",
                categoryWithProductsService.getPairOfEntities(categoryId, page, size));
        return "productsToAddInCategory";
    }

    @PostMapping(value = "/removeProductFromCategory")
    public String removeProductFromCategory(@RequestParam Long productId,
                                            @RequestParam Long categoryId) {
        categoryService.removeProductFromCategory(categoryId, productId);
        return "redirect:showCategoryPage/" + categoryId;
    }

    @RequestMapping(value = "/setIsAvailableInEditor", method = RequestMethod.POST)
    public String setIsAvailableInEditor(@RequestParam(name = "productId") Long productId,
                                         @RequestParam(name = "isAvailable", required = false) boolean isAvailable,
                                         @RequestParam(name = "categoryId") Long categoryId) {
        productService.setIsAvailableToProduct(productId, isAvailable);
        return "redirect:showCategoryPage/" + categoryId;
    }
    @RequestMapping(value = "/deleteCategory",method = RequestMethod.GET)
    public String deleteCategory(@RequestParam("categoryId") Long categoryId) throws SQLException {
        categoryService.deleteCategory(categoryId);
        return "redirect:/getCategoryPage";
    }
}
