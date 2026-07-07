package com.academy.course.appcafe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithAllRolesToEdit {

    @Min(value = 1,message = "Id employeeRequest must be positive and not equal 0")
    private Long id;

    @NotBlank(message = "Login cannot be empty")
    @Size(min = 3,max = 18,message = "length of login must be from 3 to 18 symbols")
    @Pattern(regexp = "[a-zA-Z]*",message = "Login may contain only upper and lower case letters")
    private String login;


    private Set<RoleDTO> roleDTOS = new HashSet<>();

    private List<Long> roleIds = new ArrayList<>();
}
