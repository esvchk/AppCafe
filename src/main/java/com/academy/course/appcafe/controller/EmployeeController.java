package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.annotation.ValidId;
import com.academy.course.appcafe.annotation.ValidPagination;
import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeRequest;
import com.academy.course.appcafe.dto.EmployeeWithAllRolesToEdit;
import com.academy.course.appcafe.service.EmployeeService;
import com.academy.course.appcafe.service.EmployeeWithRolesService;
import com.academy.course.appcafe.service.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@SessionAttributes("availableRoles")
@ValidId
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final EmployeeWithRolesService employeeWithRolesService;

    @ValidPagination
    @RequestMapping(value = "/getEmployeePage", method = RequestMethod.GET)
    public String paginatedEmployees(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "5") int size,
                                     Model model) {

        Page<EmployeeDTO> employeePage = employeeService.getPaginatedListOfEmployees(page, size);

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("requestedEmployee", new EmployeeRequest());

        return "employee-pages";
    }

    @RequestMapping(value = "/registerEmployee", method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid EmployeeRequest employeeRequest, BindingResult result,
                           Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "register";
        } else {
            employeeService.registerEmployee(employeeRequest);
            status.setComplete();
            return "redirect:/getEmployeePage";
        }
    }

    @RequestMapping(value = "/registerForm", method = RequestMethod.GET)
    public String getRegisterForm(Model model) {
        model.addAttribute("employeeRequest", new EmployeeRequest());
        model.addAttribute("availableRoles", roleService.findAll());
        return "register";
    }


    @RequestMapping(value = "/findEmployeeById", method = RequestMethod.GET)
    public String findEmployeeById(@RequestParam("id") Long id, Model model) {
        model.addAttribute("employeeById", employeeService.findEmployeeById(id));
        return "employeeById";
    }

    @RequestMapping(value = "/findEmployeeByName", method = RequestMethod.GET)
    public String findEmployeeByLogin(@RequestParam
                                      @NotBlank(message = "Login cannot be empty")
                                      @Size(min = 3, max = 18, message = "length of login must be from 3 to 18 symbols")
                                      @Pattern(regexp = "[a-zA-Z]*",
                                              message = "Login may contain only upper and lower case letters")
                                      String login,
                                      Model model) {

        model.addAttribute("employeeByLogin", employeeService.findEmployeeByLogin(login));
        model.addAttribute("login", login);
        return "employeeByLogin-results";
    }

    @RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
    public String showUpdateFormEmployee(@RequestParam(name = "id") Long id, Model model) {
        EmployeeWithAllRolesToEdit formData = employeeWithRolesService.getPairByEmployeeId(id);
        model.addAttribute("employeeWithRoles", formData);
        return "editEmployee-form";
    }

    @PostMapping(value = "/updateEmployee")
    public String updateEmployee(@Valid @ModelAttribute EmployeeWithAllRolesToEdit employeeEdit,
                                 BindingResult result) {
        employeeService.updateEmployee(employeeEdit.getId(), employeeEdit);
        return "redirect:/getEmployeePage";
    }

    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.GET)
    public String deleteEmployee(@RequestParam(name = "id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/getEmployeePage";
    }


}
