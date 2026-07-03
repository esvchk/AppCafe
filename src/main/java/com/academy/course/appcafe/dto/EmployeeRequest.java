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
    @Min(value = 1,message = "Id employeeRequest must be positive and not equal 0")
    private Long id;

    @NotBlank(message = "Login cannot be empty")
    @Size(min = 3,max = 18,message = "Length of login must be from 3 to 18 symbols")
    @Pattern(regexp = "[a-zA-Z]*",message = "Login may contain only upper and lower case letters")
    private String login;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6,max = 16,message = "Password must have length from 6 to 16 symbols")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}",
            message = "Password must contain at least one figure,upper and lower case letter")
    private String password;


    @NotEmpty(message = "List of roles cannot be empty")
    private List<String> roleNames = new ArrayList<>();


}
