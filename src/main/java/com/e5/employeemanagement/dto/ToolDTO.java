package com.e5.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * It is DTO class to validate the Tool Details.
 * </p>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolDTO {
    private int id;
    @NotBlank(message = "model should not be blank")
    private String name;
    @Pattern(regexp = "^\\d+(\\.\\d+){2}$")
    private String version;
    @NotBlank(message = "model should not be blank")
    private String type;
}
