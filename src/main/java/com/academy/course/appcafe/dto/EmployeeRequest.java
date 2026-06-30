package com.academy.course.appcafe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "Id employeeRequest required")
    @Min(value = 1,message = "Id employeeRequest must be positive and not equal 0")
    private Long id;

    @NotBlank
    @Size(min = 3,max = 18,message = "length of login of EmployeeRequest must be from 3 to 18 symbols")
    @Pattern(regexp = "[a-zA-Z]*",message = "Login EmployeeRequest may contain only upper and lower case letters")
    private String login;

    @NotBlank
    @Size(min = 6,max = 16,message = "Password must have length from 6 to 16 symbols")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)",
            message = "Password must contain at least one figure,upper and lower case letter")
    private String password;

    @NotBlank(message = "Employee must have roles")
    private List<String> roleNames = new ArrayList<>();


}
