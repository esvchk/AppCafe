package com.academy.course.appcafe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {


    @Min(value = 1,message = "Id must be positive and not equal 0")
    private Long id;

    @Pattern(regexp = "[A-Z]*",message = "Role must have name only with upper case letters")
    @NotNull(message = "Role must have name")
    private String name;


}
