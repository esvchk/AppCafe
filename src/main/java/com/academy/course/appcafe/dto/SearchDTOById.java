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
    @Min(value = 1,message = "Id must be bigger or equal 1")
    @NotNull(message = "Id cannot be empty")
    @Positive(message = "Id must have positive number")
    private Long id;


}
