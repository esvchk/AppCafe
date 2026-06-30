package com.academy.course.appcafe.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @NotNull(message = "Id required")
    @Min(value = 1,message = "Id must be positive and not equal 0")
    private Long id;

    @Size(min = 3,max = 50,message = "Name must have length from 3 to 50 letters")
    @Pattern(regexp = "[a-zA-Z]*",message = "Name must have only letters")
    private String name;

    @Nullable
    @Valid
    private List<ProductDTO> productDTOS = new ArrayList<>();



}
