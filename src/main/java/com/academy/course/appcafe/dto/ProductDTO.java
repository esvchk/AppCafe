package com.academy.course.appcafe.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @Min(value = 1,message = "Id must be positive and not equal 0")
    private Long id;

    @Size(min = 2,max = 20, message = "Name must have size from 2 to 20 symbols")
    @Pattern(regexp = "(^[A-Z])([a-z\\s,-]*)",message = "Name must starts with upper case letter and without figures")
    private String name;

    @DecimalMin(value = "0.01",message = "Price cannot be lower than 0.01")
    private Double price;

    @Length(max = 50,message = "Max size info - 50 symbols")
    @Nullable
    private String info;

    @Nullable
    @Min(value = 0,message = "min limit 0")
    @Max(value = 100,message = "max limit 100")
    private Integer productLimit;

    @NotNull
    private Boolean isAvailable;

    @Valid
    private CategoryDTO categoryDTO;

}
