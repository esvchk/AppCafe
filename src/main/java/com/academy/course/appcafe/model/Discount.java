package com.academy.course.appcafe.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Discount extends DataEntity{

    private BigDecimal percentOfDiscount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    private Role role ;

    @OneToMany(mappedBy = "discount",cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private List<OrderItem> orderItems;
}
