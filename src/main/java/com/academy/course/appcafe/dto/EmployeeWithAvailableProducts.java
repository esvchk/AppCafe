package com.academy.course.appcafe.dto;

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
public class EmployeeWithAvailableProducts {

    @NotNull
    private OrderDTO orderDTO;

    private Page<ProductDTO> productDTOPage;
}
