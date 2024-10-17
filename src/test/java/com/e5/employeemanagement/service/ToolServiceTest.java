package com.e5.employeemanagement.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.ToolDTO;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.model.Employee;
import com.e5.employeemanagement.model.Tool;
import com.e5.employeemanagement.repository.ToolRepository;

@ExtendWith(MockitoExtension.class)
public class ToolServiceTest {
    @Mock
    private ToolRepository toolRepository;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private ToolService toolService;
    private Tool tool;
    private Employee employee;
    private ToolDTO toolDTO;

    @BeforeEach
    public void setUp() {
        tool = Tool.builder()
                .id(1)
                .name("Intellij")
                .type("compiler")
                .version("1.0.0")
                .build();
        toolDTO = ToolDTO.builder()
                .id(1)
                .name("Intellij")
                .type("compiler")
                .version("1.0.0")
                .build();
        employee = Employee.builder()
                .id(1)
                .name("Sathees")
                .email("sathees172003@gmail.com")
                .dob(new Date(2003, 8, 17))
                .phoneNumber(9345987431L)
                .role("Developer")
                .tools(new ArrayList<>(Collections.singletonList(tool)))
                .build();
    }

    @Test
    public void testAddToolSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        when(toolRepository.existsByNameAndVersion(toolDTO.getName(), toolDTO.getVersion())).thenReturn(false);
        doNothing().when(employeeService).saveEmployee(any(Employee.class));
        ToolDTO addedTool =  toolService.addTool(toolDTO, employee.getId());
        assertNotNull(addedTool);
        assertEquals(addedTool.getName(), toolDTO.getName());
    }

    @Test
    public void testAddToolFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> toolService.addTool(toolDTO, employee.getId()));
    }

    @Test
    public void testGetToolSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertEquals(1, toolService.getToolsById(employee.getId()).size());
    }

    @Test
    public void testGetToolNotFound() {
        employee.setTools(new ArrayList<>());
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(NoSuchElementException.class, () -> toolService.getToolsById(employee.getId()));
    }

    @Test
    public void testGetToolFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> toolService.getToolsById(employee.getId()));
    }

    @Test
    public void testRemoveToolSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        doNothing().when(employeeService).saveEmployee(any(Employee.class));
        toolService.removeTool(tool.getId());
    }

    @Test
    public void testRemoveToolNotFound() {
        employee.setTools(new ArrayList<>());
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(NoSuchElementException.class, () -> toolService.removeTool(tool.getId()));
    }

    @Test
    public void testRemoveToolFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> toolService.removeTool(tool.getId()));
    }
}
