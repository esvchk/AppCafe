package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.*;
import com.academy.course.appcafe.service.EmployeeService;
import com.academy.course.appcafe.service.EmployeeWithRolesService;
import com.academy.course.appcafe.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@SessionAttributes("availableRoles")
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
        if (!model.containsAttribute("searchByIdRequest")) {
            model.addAttribute("searchByIdRequest",new SearchDTOById());
        }
        if (!model.containsAttribute("searchByNameRequest")) {
            model.addAttribute("searchByNameRequest",new SearchDTOByName());
        }
        model.addAttribute("requestedEmployee",new EmployeeRequest());

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
    public String findEmployeeById(@Valid @ModelAttribute(name = "searchByIdRequest") SearchDTOById searchDTOById,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes attributes) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            attributes.addFlashAttribute("invalidId",errorMessage);
            attributes.addFlashAttribute("searchByIdRequest",searchDTOById);
            return "redirect:/getEmployeePage";
        }
        model.addAttribute("employeeById",employeeService.findEmployeeById(searchDTOById.getId()));
        return "employeeById";
    }

    @RequestMapping(value = "/findEmployeeByName",method = RequestMethod.GET)
    public String findEmployeeByLogin(@Valid @ModelAttribute(name = "searchByNameRequest") SearchDTOByName searchDTOByName,
                                      BindingResult result,
                                      Model model,
                                      RedirectAttributes attributes){
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            attributes.addFlashAttribute("invalidName",errorMessage);
            attributes.addFlashAttribute("searchByNameRequest",searchDTOByName);
            return "redirect:/getEmployeePage";
        }
        model.addAttribute("employeeByLogin",employeeService.findEmployeeByLogin(searchDTOByName.getLogin()));
        model.addAttribute("login", searchDTOByName.getLogin());
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
