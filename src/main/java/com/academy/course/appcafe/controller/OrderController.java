package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeRequest;
import com.academy.course.appcafe.dto.OrderDTO;
import com.academy.course.appcafe.service.EmployeeWithOrderAndAvailableProductsService;
import com.academy.course.appcafe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final EmployeeWithOrderAndAvailableProductsService pairService;

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

    @PostMapping(value = "/addNewOrder")
    public String addNewOrder(Principal principal){
        String login =  principal.getName();
        OrderDTO order = orderService.addNewOrderToEmployeeByLogin(login);
        return "redirect:/newOrderPage/" + order.getId();
    }

    @GetMapping (value = "/newOrderPage/{orderId}")
    public String newOrder( Model model,
                           @PathVariable Long orderId,
                           @RequestParam(value = "page",defaultValue = "0")int page,
                           @RequestParam(value = "size",defaultValue = "10")int size,
                           Principal principal){
        String loginEmployee = principal.getName();
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("orderWithProducts",pairService.getPairEntitiesByEmployeeLogin(loginEmployee,orderId,page,size));
        return "productsToAddInOrder";
    }
    @RequestMapping(value = "/buyOrder", method = RequestMethod.GET)
    public String buyOrder(@RequestParam("id") Long orderId) throws SQLException {
        orderService.buyOrder(orderId);
        return "redirect:/newOrder";
    }

    @PostMapping(value = "/addProductInOrder/{orderId}")
    public String addProductInOrder(@RequestParam("orderId") Long orderId,
                                    @RequestParam("productId") Long productId,
                                    @RequestParam ("quantity") Integer quantity) throws SQLException {
        orderService.addProductToOrder(productId,orderId,quantity);
        return "redirect:/newOrderPage/" + orderId;
    }



}
