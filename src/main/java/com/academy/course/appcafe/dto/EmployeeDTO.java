package com.academy.course.appcafe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDTO {
    private Long id;

    private String login;

    private RoleDTO roleDTO;

    private List<OrderDTO> orderDTOs;

}
