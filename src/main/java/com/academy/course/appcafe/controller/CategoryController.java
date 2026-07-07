package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.annotation.ValidId;
import com.academy.course.appcafe.annotation.ValidPagination;
import com.academy.course.appcafe.dto.CategoryDTO;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.service.CategoryService;
import com.academy.course.appcafe.service.CategoryWithProductsService;
import com.academy.course.appcafe.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryWithProductsService categoryWithProductsService;
    private final ProductService productService;

    @ValidPagination
    @RequestMapping(value = "/getCategoryPage", method = RequestMethod.GET)
    public String paginatedCategories(@RequestParam(value = "page", defaultValue = "0")
                                      int page,
                                      @RequestParam(value = "size", defaultValue = "5")
                                      int size,
                                      Model model) {

        Page<CategoryDTO> categoryPage = categoryService.getPaginatedCategories(page, size);

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("category", new CategoryDTO());
        return "category-pages";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@Valid @ModelAttribute(name = "category")
                              CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "add-category";
        }

        categoryService.createCategory(categoryDTO);

        return "redirect:/getCategoryPage";
    }

    @GetMapping(value = "/addingCategoryForm")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "add-category";
    }

    @ValidId
    @PostMapping(value = "/addProductInCategory")
    public String addProductInCategory(@RequestParam("categoryId") Long categoryId,
                                       @RequestParam("productId") Long productId) {
        categoryService.addProductToCategory(categoryId, productId);
        return "redirect:/showCategoryPage/" + categoryId;
    }

    @ValidPagination
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

    @ValidId
    @PostMapping(value = "/removeProductFromCategory")
    public String removeProductFromCategory(@RequestParam Long productId,
                                            @RequestParam Long categoryId) {
        categoryService.removeProductFromCategory(categoryId, productId);
        return "redirect:showCategoryPage/" + categoryId;
    }

    @ValidId
    @RequestMapping(value = "/setIsAvailableInEditor", method = RequestMethod.POST)
    public String setIsAvailableInEditor(@RequestParam(name = "productId")
                                         Long productId,
                                         @RequestParam(name = "isAvailable", required = false)
                                         boolean isAvailable,
                                         @RequestParam(name = "categoryId")
                                         Long categoryId) {
        productService.setIsAvailableToProduct(productId, isAvailable);
        return "redirect:showCategoryPage/" + categoryId;
    }

    @ValidId
    @RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
    public String deleteCategory(@RequestParam("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return "redirect:/getCategoryPage";
    }

    @RequestMapping(value = "/findCategoryByName", method = RequestMethod.GET)
    public String findCategoryByName(@RequestParam(name = "name")
                                         @NotBlank(message = "Category cannot be empty")
                                         @Length(min = 3,max = 50,message = "Name must have length from 3 to 20 letters")
                                         @Pattern(regexp = "[a-zA-Z]*",message = "Name must have only letters")
                             String name,
                             Model model) {
        model.addAttribute("categoryByName", categoryService.getCategoryByName(name));
        return "categoryByName-result";
    }

    @ValidId
    @RequestMapping(value = "/findCategoryById", method = RequestMethod.GET)
    public String findCategoryById(@RequestParam("id") Long id,
                           Model model) {
        model.addAttribute("categoryById", categoryService.getCategoryById(id));
        return "categoryById-result";
    }

    @ValidId
    @GetMapping(value = "/showEditCategoryForm")
    public String showEditCategoryForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "editCategory-form";
    }

    @PostMapping(value = "/updateCategory")
    public String updateCategory(@Valid @ModelAttribute(name = "category") CategoryDTO newValue,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "editCategory-form";
        }
        categoryService.updateCategory(newValue.getId(), newValue);
        return "redirect:/getCategoryPage";
    }
}
