package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.*;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.service.EmployeeService;
import com.academy.course.appcafe.service.EmployeeWithRolesService;
import com.academy.course.appcafe.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
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
        return "employee-pages";
    }

    @RequestMapping(value = "/registerEmployee",method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid EmployeeRequest employeeRequest) throws SQLException {

        employeeService.registerEmployee(employeeRequest);
        return "redirect:/getEmployeePage";
    }

    @RequestMapping(value = "/registerForm",method = RequestMethod.GET)
    public String getRegisterForm(Model model){
        model.addAttribute("employeeRequest",new EmployeeRequest());
        model.addAttribute("availableRoles",roleService.findAll());
        return "register";
    }

    @RequestMapping(value = "/findEmployeeById",method = RequestMethod.GET)
    public String findEmployeeById(@RequestParam("id")Long id,
                           Model model) throws SQLException {
        model.addAttribute("employeeById",employeeService.findEmployeeById(id));
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
    public String showUpdateFormEmployee(@RequestParam("id") Long oldValueId, Model model) throws SQLException {
        model.addAttribute("employeeWithRoles", employeeWithRolesService.getPairByEmployeeId(oldValueId));
        return "editEmployee-form";
    }

    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    public String updateEmployee(EmployeeEdit employeeEdit) throws SQLException {
        employeeService.updateEmployee(employeeEdit.getId(), employeeEdit);
        return "redirect:/getEmployeePage";
    }

    @RequestMapping(value = "/deleteEmployee",method = RequestMethod.GET)
    public String deleteEmployee(@RequestParam("id")Long id) throws SQLException {
        employeeService.deleteEmployee(id);
        return "redirect:/getEmployeePage";
    }


}
