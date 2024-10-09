package com.e5.employeemanagement.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * It is DTO class to validate the Employee Details.
 * </p>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private int id;
    @Pattern(regexp= "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "Name should be alphabets" )
    private String name;
    @Past(message = "Date should not be in future")
    private Date dob;
    @Email(message = "should contain @gmail.com")
    private String email;
    @NotBlank(message = "Role should not be blank")
    private String role;
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    private long phoneNumber;
    private LaptopDTO laptop;
    private TeamDTO team;
    private List<ToolDTO> tools;
}
