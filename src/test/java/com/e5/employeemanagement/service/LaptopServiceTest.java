package com.e5.employeemanagement.service;

import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.LaptopDTO;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.model.Employee;
import com.e5.employeemanagement.model.Laptop;
import com.e5.employeemanagement.repository.LaptopRepository;

@ExtendWith(MockitoExtension.class)
public class LaptopServiceTest {
    @Mock
    private LaptopRepository laptopRepository;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private LaptopService laptopService;
    private LaptopDTO laptopDTO;
    private Laptop laptop;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        laptop = Laptop.builder()
                .id(1)
                .model("Tuf")
                .model("Asus")
                .os("Windows")
                .build();
        laptopDTO = LaptopDTO.builder()
                .id(1)
                .model("Tuf")
                .model("Asus")
                .os("Windows")
                .build();
        employee = Employee.builder()
                .id(1)
                .name("Sathees")
                .email("sathees172003@gmail.com")
                .dob(new Date(2003, 8, 17))
                .phoneNumber(9345987431L)
                .role("Developer")
                .laptop(laptop)
                .build();
    }

    @Test
    public void testAddLaptopSuccess() {
        employee.setLaptop(null);
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        doNothing().when(employeeService).saveEmployee(employee);
        LaptopDTO result = laptopService.addLaptop(laptopDTO, employee.getId());
        assertNotNull(result);
        assertEquals(result.getModel(), laptop.getModel());
    }

    @Test
    public void testAddLaptopAlreadyExists() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(DuplicateKeyException.class, () -> laptopService.addLaptop(laptopDTO, employee.getId()));
    }

    @Test
    public void testAddLaptopNotFound() {
        when(employeeService.getEmployee(anyInt())).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> laptopService.addLaptop(laptopDTO, employee.getId()));
    }

    @Test
    public void testAddLaptopFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> laptopService.addLaptop(laptopDTO, employee.getId()));
    }

    @Test
    public void testGetLaptopSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertNotNull(laptopService.getLaptopById(employee.getId()));
    }

    @Test
    public void testGetLaptopNotFound() {
        employee.setLaptop(null);
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(NoSuchElementException.class, () -> laptopService.getLaptopById(employee.getId()));
    }

    @Test
    public void testGetLaptopFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> laptopService.getLaptopById(employee.getId()));
    }

    @Test
    public void testUpdateLaptopSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        doNothing().when(employeeService).saveEmployee(employee);
        LaptopDTO result = laptopService.updateLaptop(laptopDTO, employee.getId());
        assertNotNull(result);
        assertEquals(result.getModel(), laptop.getModel());
    }

    @Test
    public void testUpdateLaptopNotFound() {
        employee.setLaptop(null);
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(NoSuchElementException.class, () -> laptopService.updateLaptop(laptopDTO, employee.getId()));
    }

    @Test
    public void testUpdateLaptopFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> laptopService.updateLaptop(laptopDTO, employee.getId()));
    }

    @Test
    public void testRemoveLaptopSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        when(laptopRepository.save(any())).thenReturn(laptop);
        doNothing().when(employeeService).saveEmployee(employee);
        laptopService.removeLaptop(employee.getId());
    }

    @Test
    public void testRemoveLaptopNotFound() {
        employee.setLaptop(null);
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(NoSuchElementException.class, () -> laptopService.removeLaptop(employee.getId()));
    }

    @Test
    public void testRemoveLaptopFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> laptopService.removeLaptop(employee.getId()));
    }
}
