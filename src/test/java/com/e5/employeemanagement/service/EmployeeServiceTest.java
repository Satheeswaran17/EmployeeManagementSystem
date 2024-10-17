package com.e5.employeemanagement.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.EmployeeDTO;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.model.Employee;
import com.e5.employeemanagement.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;
    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void setUp() {
       employee = Employee.builder()
               .id(1)
               .name("Sathees")
               .email("sathees172003@gmail.com")
               .dob(new Date(2003, 8, 17))
               .phoneNumber(9345987431L)
               .role("Developer")
               .build();
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
        when(employeeRepository.existsByPhoneNumberOrEmail(anyLong(), anyString())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDTO addedEmployee = employeeService.addEmployee(employeeDTO);
        assertNotNull(addedEmployee);
        assertEquals(addedEmployee.getEmail(), employeeDTO.getEmail());
    }

    @Test
    public void testAddEmployeeFailure() {
        when(employeeRepository.existsByPhoneNumberOrEmail(anyLong(), anyString())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> employeeService.addEmployee(employeeDTO));
    }

    @Test
    public void testAddEmployeeAlreadyExists() {
        when(employeeRepository.existsByPhoneNumberOrEmail(anyLong(), anyString())).thenReturn(true);
        assertThrows(DuplicateKeyException.class, () -> employeeService.addEmployee(employeeDTO));
    }

    @Test
    public void testGetEmployeeByIdSuccess() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenReturn(employee);
        EmployeeDTO fetchedEmployee = employeeService.getEmployeeById(employeeDTO.getId());
        assertNotNull(fetchedEmployee);
        assertEquals(employeeDTO.getId(), fetchedEmployee.getId());
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> employeeService.getEmployeeById(employeeDTO.getId()));
    }

    @Test
    public void testGetEmployeeByIdFailure() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> employeeService.getEmployeeById(employeeDTO.getId()));
    }

    @Test
    public void testGetAllEmployeeSuccess() {
        when(employeeRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(List.of(employee));
        assertEquals(1, employeeService.getAllEmployees(0, 1).size());
    }

    @Test
    public void testGetAllEmployeeFailure() {
        when(employeeRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> employeeService.getAllEmployees(0, 1));
    }

    @Test
    public void testUpdateEmployeeSuccess() {
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDTO);
        assertNotNull(updatedEmployee);
        assertEquals(employeeDTO.getId(), updatedEmployee.getId());
    }

    @Test
    public void testUpdateEmployeeNotFound() {
        when(employeeRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> employeeService.updateEmployee(employeeDTO));
    }

    @Test
    public void testUpdateEmployeeFailure() {
        when(employeeRepository.existsById(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> employeeService.updateEmployee(employeeDTO));
    }

    @Test
    public void testRemoveEmployeeSuccess() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        employeeService.removeEmployee(employeeDTO.getId());
    }

    @Test
    public void testRemoveEmployeeNotFound() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> employeeService.removeEmployee(employeeDTO.getId()));
    }
    @Test
    public void testRemoveEmployeeFailure() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> employeeService.removeEmployee(employeeDTO.getId()));
    }

    @Test
    public void testGetEmployeeSuccess() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenReturn(employee);
        Employee fetchedEmployee = employeeService.getEmployee(employeeDTO.getId());
        assertNotNull(fetchedEmployee);
        assertEquals(employeeDTO.getId(), fetchedEmployee.getId());
    }

    @Test
    public void testGetEmployeeNotFound() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> employeeService.getEmployee(employeeDTO.getId()));
    }

    @Test
    public void testGetEmployeeFailure() {
        when(employeeRepository.findByIdAndIsDeletedFalse(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> employeeService.getEmployee(employeeDTO.getId()));
    }

    @Test
    public void testSaveEmployeeSuccess() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        employeeService.saveEmployee(employee);
    }

    @Test
    public void testSaveEmployeeFailure() {
        when(employeeRepository.save(any(Employee.class))).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> employeeService.saveEmployee(employee));
    }
}

