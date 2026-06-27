package com.academy.course.appcafe.controller;

import com.academy.course.appcafe.dto.EmployeeDTO;
import com.academy.course.appcafe.dto.EmployeeRequest;
import com.academy.course.appcafe.dto.ProductDTO;
import com.academy.course.appcafe.dto.RoleDTO;
import com.academy.course.appcafe.repository.EmployeeRepository;
import com.academy.course.appcafe.service.EmployeeService;
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

    @RequestMapping(value = "/getEmployeePage", method = RequestMethod.GET)
    public String paginatedProducts(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                    @RequestParam(value = "size", defaultValue = "5") int size, Model model) {

        Page<EmployeeDTO> employeePage = employeeService.getPaginatedListOfEmployees(offset, size);

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("offset", offset);
        model.addAttribute("size", size);
        model.addAttribute("requestedEmployee",new EmployeeRequest());
        return "employee-pages";
    }

    @RequestMapping(value = "/registerEmployee",method = RequestMethod.POST)
    public String register(EmployeeRequest employeeRequest) throws SQLException {

        employeeService.registerEmployee(employeeRequest);
        return "redirect:/getEmployeePage";
    }


}
