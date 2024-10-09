package com.e5.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * It is DTO class to validate the Team Details.
 * </p>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private int id;
    @NotBlank(message = "model should not be blank")
    private String domain;
    @NotBlank(message = "model should not be blank")
    private String project;
    @Pattern(regexp= "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "LeadName should be alphabets" )
    private String leadName;
}
