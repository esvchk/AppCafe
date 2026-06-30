package com.academy.course.appcafe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOfEmployeeWithAvailableProducts {

    @NotNull(message = "Order cannot be empty")
    @Valid
    private OrderDTO orderDTO;

    @NotBlank(message = "Page cannot be empty")
    @Valid
    private Page<ProductDTO> productDTOPage;
}
