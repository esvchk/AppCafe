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
public class SearchDTOByName {

    @NotBlank(message = "Login cannot be empty")
    @Size(min = 3,max = 18,message = "length of login must be from 3 to 18 symbols")
    @Pattern(regexp = "[a-zA-Z]*",message = "Login may contain only upper and lower case letters")
    private String login;
}
