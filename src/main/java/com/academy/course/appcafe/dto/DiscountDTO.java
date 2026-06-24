package com.academy.course.appcafe.dto;

import com.academy.course.appcafe.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {

    private BigDecimal percentOfDiscount;

    private Set<Role> roles;
}
