package com.e5.employeemanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.helper.AuthenticateException;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.model.Users;
import com.e5.employeemanagement.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthenticationDetailsServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthenticationDetailsService authenticationDetailsService;
    private Users users;

    @BeforeEach
    public void setUp() {
        users = Users.builder()
                .id(1)
                .userName("sathees172003@gmail.com")
                .password("Sathees172003@")
                .build();
    }

    @Test
    public void loadUserByUsernameSuccess() {
        when(userRepository.findByUserName(anyString())).thenReturn(users);
        UserDetails userDetails = authenticationDetailsService.loadUserByUsername(users.getUserName());
        assertNotNull(userDetails);
        assertEquals(users.getUserName(), userDetails.getUsername());
    }

    @Test
    public void loadUserByUsernameNotFound() {
        when(userRepository.findByUserName(anyString())).thenReturn(null);
        assertThrows(AuthenticateException.class, () -> authenticationDetailsService.loadUserByUsername(users.getUserName()));
    }

    @Test
    public void loadUserByUsernameFailure() {
        when(userRepository.findByUserName(anyString())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> authenticationDetailsService.loadUserByUsername(users.getUserName()));
    }
}