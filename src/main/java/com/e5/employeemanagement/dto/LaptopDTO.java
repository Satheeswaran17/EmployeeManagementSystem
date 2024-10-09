package com.e5.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * It is DTO class to validate the Laptop Details.
 * </p>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaptopDTO {
    private int id;
    @NotBlank(message = "model should not be blank")
    private String model;
    @NotBlank(message = "model should not be blank")
    private String brand;
    @NotBlank(message = "model should not be blank")
    private String os;
}
