package com.e5.employeemanagement.mapper;

import java.util.stream.Collectors;

import com.e5.employeemanagement.dto.EmployeeDTO;
import com.e5.employeemanagement.model.Employee;

/**
 * <p>
 * It is class Maps the DTO to Model and model to DTO for Employee.
 * </p>
 */
public class EmployeeMapper {
    /**
     * <p>
     * It is the method to map the employee model to employee dto using employee dto builder.
     * </p>
     *
     * @param employee {@link Employee} it contains employee details to use a map model to dto.
     * @return {@link EmployeeDTO}. it contains restricted employee details, such as id, name, email, role
     *           and phone number.
     */
    public static EmployeeDTO employeeToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .role(employee.getRole())
                .phoneNumber(employee.getPhoneNumber()).build();
    }

    /**
     * <p>
     * It is the method to map the employee dto to employee model using employee builder.
     * </p>
     *
     * @param employeeDto {@link Employee} it contains employee details to use to map dto to model.
     * @return {@link Employee}. it contains all details of employee.
     */
    public static Employee dtoToEmployee(EmployeeDTO employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .dob(employeeDto.getDob())
                .email(employeeDto.getEmail())
                .role(employeeDto.getRole())
                .phoneNumber(employeeDto.getPhoneNumber())
                .laptop(LaptopMapper.dtoToLaptop(employeeDto.getLaptop()))
                .team(TeamMapper.dtoToTeam(employeeDto.getTeam()))
                .tools(employeeDto.getTools().stream().map(ToolMapper::dtoToTool)
                        .collect(Collectors.toList())).build();
    }

    /**
     * <p>
     * It is the method to map the employee model to employee dto it contains all details using employee builder.
     * </p>
     *
     * @param employee {@link Employee} it contains employee details to use to map model to dto.
     * @return {@link EmployeeDTO}. it contains all details of employee.
     */
    public static EmployeeDTO employeeToReturnDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .dob(employee.getDob())
                .role(employee.getRole())
                .phoneNumber(employee.getPhoneNumber())
                .laptop(LaptopMapper.laptopToDTO(employee.getLaptop()))
                .team(TeamMapper.teamToDTO(employee.getTeam()))
                .tools(employee.getTools().stream().map(ToolMapper::toolToDTO).collect(Collectors.toList())).build();
    }
}
