package com.academy.course.appcafe.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDTO {
    @Min(value = 1,message = "Id must be positive and not equal 0")
    private Long id;

    @NotBlank(message = "Login cannot be empty")
    @Size(min = 3,max = 18,message = "length of login must be from 3 to 18 symbols")
    @Pattern(regexp = "[a-zA-Z]*",message = "Login may contain only upper and lower case letters")
    private String login;

    @NotBlank
    @Valid
    private Set<RoleDTO> roleDTOS = new HashSet<>();

    @Nullable
    @Valid
    private List<OrderDTO> orderDTOS;

}
