package com.academy.course.appcafe.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTOById {
    @NotNull(message = "Id cannot be empty")
    @Positive(message = "Id must have positive number")
    private Long id;


}
