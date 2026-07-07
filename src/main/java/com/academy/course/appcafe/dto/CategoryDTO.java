package com.academy.course.appcafe.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @Min(value = 1,message = "Id must be positive and not equal 0")
    private Long id;

    @NotBlank(message = "Category cannot be empty")
    @Length(min = 3,max = 50,message = "Name must have length from 3 to 20 letters")
    @Pattern(regexp = "[a-zA-Z]*",message = "Name must have only letters")
    private String name;

    @Nullable
    private List<ProductDTO> productDTOS = new ArrayList<>();



}
