package com.academy.course.appcafe.dto;

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

    @NotNull
    private OrderDTO orderDTO;

    private Page<ProductDTO> productDTOPage;
}
