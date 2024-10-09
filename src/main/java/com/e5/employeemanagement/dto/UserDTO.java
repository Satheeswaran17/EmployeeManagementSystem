package com.e5.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * It is DTO class to validate the Users Details.
 * </p>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    @Email(message = "should contain @gmail.com")
    private String userName;
    @NotBlank
    private String password;
}
