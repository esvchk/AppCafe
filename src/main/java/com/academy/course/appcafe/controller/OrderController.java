package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.annotation.ValidId;
import com.academy.course.appcafe.annotation.ValidPagination;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.dto.ProductOrderRequest;
import com.academy.course.appcafe.service.OrderOfEmployeeWithAvailableProductsService;
import com.academy.course.appcafe.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor

public class OrderController {
    private final OrderService orderService;
    private final OrderOfEmployeeWithAvailableProductsService pairService;

    @ValidPagination
    @RequestMapping(value = "/getOrderPage", method = RequestMethod.GET)
    public String paginatedOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "5") int size, Model model) {

        Page<OrderDTO> employeePage = orderService.getPaginatedListOfOrders(page, size);

        model.addAttribute("orderPage", employeePage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("order", new OrderDTO());
        return "order-pages";
    }

    @PostMapping(value = "/addNewOrder")
    public String addNewOrder(Principal principal) {
        String login = principal.getName();
        OrderDTO order = orderService.addNewOrderToEmployeeByLogin(login);
        return "redirect:/newOrderPage/" + order.getId();
    }

    @ValidId
    @ValidPagination
    @GetMapping(value = "/newOrderPage/{orderId}")
    public String newOrder(@PathVariable Long orderId,
                           Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           Principal principal) {
        String loginEmployee = principal.getName();
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("orderWithProducts",
                pairService.getPairEntitiesByEmployeeLogin(loginEmployee, orderId, page, size));
        return "productsToAddInOrder";
    }


    @ValidId
    @PostMapping(value = "/buyOrder")
    public String buyOrder(@RequestParam Long orderId) {
        orderService.buyOrder(orderId);
        return "redirect:/permitPurchase?orderId=" + orderId;
    }

    @ValidId
    @GetMapping(value = "/permitPurchase")
    public String showPermitForm(@RequestParam Long orderId,
                                 Model model) {
        model.addAttribute("orderId", orderId);
        return "permit-form";
    }

    @ValidId
    @PostMapping(value = "/purchasingProcess/{orderId}")
    public String purchasingProcess(@PathVariable
                                    Long orderId,
                                    @NotNull(message = "Payment data cannot be empty")
                                    @RequestParam(name = "paymentData")
                                    String paymentData) {
        orderService.inputPaymentDataToOrder(paymentData, orderId);
        return "redirect:/getOrderPage";
    }

    @PostMapping(value = "/addProductInOrder/{orderId}")
    public String addProductInOrder(@Valid @ModelAttribute ProductOrderRequest request, BindingResult result) {
        orderService.addProductToOrder(request.getProductId(), request.getOrderId(), request.getQuantity());
        return "redirect:/newOrderPage/" + request.getOrderId();
    }


    @ValidId
    @PostMapping(value = "/removeProductFromOrder")
    public String removeProductFromOrder(@RequestParam(name = "orderId") Long orderId,
                                         @RequestParam(name = "itemId") Long itemId,
                                         @RequestParam(name = "quantity") Integer quantity) {
        orderService.deleteItemFromOrder(itemId, orderId, quantity);
        return "redirect:/newOrderPage/" + orderId;
    }

    @ValidId
    @GetMapping(value = "/findOrderById")
    public String findOrderById(@RequestParam(name="id")Long id,Model model){
        model.addAttribute("orderById",orderService.findOrderById(id));
        return "orderById";
    }

}
