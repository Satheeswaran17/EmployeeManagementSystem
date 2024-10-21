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
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.UserDTO;
import com.e5.employeemanagement.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private String token;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = UserDTO.builder()
                .id(1)
                .userName("sathees172003@gmail.com")
                .password("sathees172003@")
                .build();
        token = "token";
    }

    @Test
    public void testRegisterUserSuccess() {
        when(userService.register(any(UserDTO.class))).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.registerUser(userDTO);
        assertNotNull(response);
        assertEquals(userDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testLoginUserSuccess() {
        when(userService.createSession(any(UserDTO.class))).thenReturn(token);
        ResponseEntity<String> response = userController.login(userDTO);
        assertNotNull(response);
        assertEquals(token, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
