package com.academy.course.appcafe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;

    private String name;

    private Double price;

    private String info;

    private Integer productLimit;

    private Boolean isAvailable;

    private CategoryDTO categoryDTO;

}
