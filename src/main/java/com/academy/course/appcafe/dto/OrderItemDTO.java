package com.academy.course.appcafe.dto;

import com.academy.course.appcafe.model.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long id;

    private ProductDTO productDTO;

    private Integer quantity;

    private Discount discount;
}
