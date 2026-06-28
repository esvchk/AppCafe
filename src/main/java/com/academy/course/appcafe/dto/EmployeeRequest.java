package com.academy.course.appcafe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private Long id;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private List<String> roleNames = new ArrayList<>();


}
