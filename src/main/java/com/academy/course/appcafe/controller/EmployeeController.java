package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.*;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.service.EmployeeService;
import com.academy.course.appcafe.service.EmployeeWithRolesService;
import com.academy.course.appcafe.service.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
@SessionAttributes("availableRoles")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final EmployeeWithRolesService employeeWithRolesService;

    @RequestMapping(value = "/getEmployeePage", method = RequestMethod.GET)
    public String paginatedEmployees(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                    @RequestParam(value = "size", defaultValue = "5") int size, Model model) {

        Page<EmployeeDTO> employeePage = employeeService.getPaginatedListOfEmployees(offset, size);

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("offset", offset);
        model.addAttribute("size", size);
        model.addAttribute("requestedEmployee",new EmployeeRequest());
        model.addAttribute("searchRequest",new SearchDTOId());
        return "employee-pages";
    }

    @RequestMapping(value = "/registerEmployee",method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid  EmployeeRequest employeeRequest, BindingResult result,
                           Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("errors",result.getAllErrors());
            return "register";
        } else {
            employeeService.registerEmployee(employeeRequest);
            status.setComplete();
            return "redirect:/getEmployeePage";
        }
    }

    @RequestMapping(value = "/registerForm",method = RequestMethod.GET)
    public String getRegisterForm(Model model){
        model.addAttribute("employeeRequest",new EmployeeRequest());
        model.addAttribute("availableRoles",roleService.findAll());
        return "register";
    }


    @RequestMapping(value = "/findEmployeeById",method = RequestMethod.GET)
    public String findEmployeeById(@Valid @ModelAttribute SearchDTOId searchDTOId, BindingResult result,
                                   Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError("id").getDefaultMessage();
            attributes.addFlashAttribute("error",errorMessage);
            attributes.addFlashAttribute("invalidId",searchDTOId.getId());
            return "redirect:/getEmployeePage";
        }
        model.addAttribute("employeeById",employeeService.findEmployeeById(searchDTOId.getId()));
        return "employeeById";
    }

    @RequestMapping(value = "/findEmployeeByName",method = RequestMethod.GET)
    public String findEmployeeByLogin(@RequestParam("login")String login,
                             Model model){
        model.addAttribute("employeeByLogin",employeeService.findEmployeeByLogin(login));
        model.addAttribute("login",login);
        return "employeeByLogin-results";
    }

    @RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
    public String showUpdateFormEmployee(@RequestParam("id") Long oldValueId, Model model){
        model.addAttribute("employeeWithRoles", employeeWithRolesService.getPairByEmployeeId(oldValueId));
        return "editEmployee-form";
    }

    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    public String updateEmployee(EmployeeEdit employeeEdit) {
        employeeService.updateEmployee(employeeEdit.getId(), employeeEdit);
        return "redirect:/getEmployeePage";
    }

    @RequestMapping(value = "/deleteEmployee",method = RequestMethod.GET)
    public String deleteEmployee(@RequestParam("id")Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/getEmployeePage";
    }


}
