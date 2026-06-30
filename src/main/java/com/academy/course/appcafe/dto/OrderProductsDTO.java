package com.academy.course.appcafe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsDTO {

    @NotNull(message = "Product list cannot be Empty")
    List<ProductDTO> products;

    @Valid
    @NotNull(message = "Order cannot be null")
    OrderDTO orderDTO;
}
