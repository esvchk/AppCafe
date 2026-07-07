package com.academy.course.appcafe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWithProducts {

    @NotBlank(message = "Page cannot be empty")
    private Page<ProductDTO> productPage;

    @Valid
    @NotNull(message = "Category cannot be empty")
    private CategoryDTO categoryDTO;
}
