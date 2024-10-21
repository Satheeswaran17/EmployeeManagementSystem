package com.e5.employeemanagement.controller;

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

import com.e5.employeemanagement.dto.LaptopDTO;
import com.e5.employeemanagement.service.LaptopService;

@ExtendWith(MockitoExtension.class)
public class LaptopControllerTest {
    @Mock
    private LaptopService laptopService;
    @InjectMocks
    private LaptopController laptopController;
    private LaptopDTO laptopDTO;
    private int employeeId;

    @BeforeEach
    public void setUp() {
        laptopDTO = LaptopDTO.builder()
                .id(1)
                .model("Tuf")
                .model("Asus")
                .os("Windows")
                .build();
        employeeId = 1;
    }

    @Test
    public void testAddLaptopSuccess() {
        when(laptopService.addLaptop(any(LaptopDTO.class), anyInt())).thenReturn(laptopDTO);
        ResponseEntity<LaptopDTO> response = laptopController.addLaptop(laptopDTO, employeeId);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(laptopDTO, response.getBody());
    }

    @Test
    public void testGetLaptopSuccess() {
        when(laptopService.getLaptopById(anyInt())).thenReturn(laptopDTO);
        ResponseEntity<LaptopDTO> response = laptopController.getLaptopById(employeeId);
        assertNotNull(response);
        assertEquals(laptopDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateLaptopSuccess() {
        when(laptopService.updateLaptop(any(LaptopDTO.class), anyInt())).thenReturn(laptopDTO);
        ResponseEntity<LaptopDTO> response = laptopController.updateLaptop(laptopDTO, employeeId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(laptopDTO, response.getBody());
    }

    @Test
    public void testRemoveLaptopSuccess() {
        doNothing().when(laptopService).removeLaptop(anyInt());
        ResponseEntity<HttpStatus> response = laptopController.removeLaptop(employeeId);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
