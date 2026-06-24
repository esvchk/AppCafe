package com.academy.course.appcafe.dto;

import com.academy.course.appcafe.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDTO {
    private Integer id;

    private String login;

    private Role role;

    private Set<OrderDTO> orderDTOs;


}
