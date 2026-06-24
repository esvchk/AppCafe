package com.academy.course.appcafe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsDTO {
    Set<ProductDTO> products;

    OrderDTO orderDTO;
}
