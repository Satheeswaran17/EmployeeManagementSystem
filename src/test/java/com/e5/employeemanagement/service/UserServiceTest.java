package com.e5.employeemanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.UserDTO;
import com.e5.employeemanagement.helper.AuthenticateException;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.model.Users;
import com.e5.employeemanagement.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private UserService userService;
    private Users users;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        users = Users.builder()
                .id(1)
                .userName("sathees172003@gmail.com")
                .password("Sathees172003@")
                .build();
        userDTO = UserDTO.builder()
                .id(1)
                .userName("sathees172003@gmail.com")
                .password("Sathees172003@")
                .build();
    }

    @Test
    public void testRegisterUser() {
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(users);
        UserDTO result = userService.register(userDTO);
        assertNotNull(result);
        assertEquals(userDTO.getUserName(), result.getUserName());
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        when(userRepository.existsByUserName(anyString())).thenReturn(true);
        assertThrows(DuplicateKeyException.class, () -> userService.register(userDTO));
    }

    @Test
    public void testRegisterUserFailure() {
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> userService.register(userDTO));
    }

    @Test
    public void testCreateSessionSuccess() {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        assertInstanceOf(String.class, userService.createSession(userDTO));
    }

    @Test
    public void testCreateSessionFailure() {
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
        assertThrows(AuthenticateException.class, () -> userService.createSession(userDTO));
    }
}
