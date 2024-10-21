package com.e5.employeemanagement.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.EmployeeDTO;
import com.e5.employeemanagement.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeController employeeController;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void setUp() {
        employeeDTO = EmployeeDTO.builder()
                .id(1)
                .name("Sathees")
                .email("sathees172003@gmail.com")
                .dob(new Date(2003, 8, 17))
                .phoneNumber(9345987431L)
                .role("Developer")
                .build();
    }

    @Test
    public void testAddEmployeeSuccess() {
        when(employeeService.addEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);
        ResponseEntity<EmployeeDTO> response = employeeController.addEmployee(employeeDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
    }

    @Test
    public void testGetEmployeeSuccess() {
        when(employeeService.getEmployeeById(anyInt())).thenReturn(employeeDTO);
        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(employeeDTO.getId());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testGetAllEmployeesSuccess() {
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO);
        when(employeeService.getAllEmployees(anyInt(), anyInt())).thenReturn(employees);
        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployees(0, 10);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
    }

    @Test
    public void testUpdateEmployeeSuccess() {
        when(employeeService.updateEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);
        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(employeeDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testRemoveEmployeeSuccess() {
        doNothing().when(employeeService).removeEmployee(anyInt());
        ResponseEntity<HttpStatus> response = employeeController.removeEmployee(employeeDTO.getId());
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
