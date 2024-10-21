package com.e5.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e5.employeemanagement.dto.UserDTO;
import com.e5.employeemanagement.model.Users;
import com.e5.employeemanagement.service.UserService;

/**
 * <p>
 * It is class to handling request and response for User details.
 * </p>
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *<p>
     * Handles HTTP POST request to register a new user using user dto.
     * </p>
     *
     * @param userDTO {@link Users} it contains the user details to be registered.
     * @return {@link ResponseEntity} with the registered user details and HTTP status CREATED.
     */
    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser(@Validated @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.register(userDTO), HttpStatus.CREATED);
    }

    /**
     *<p>
     * Handles HTTP POST request to login user using users.
     * </p>
     *
     * @param userDTO {@link Users} it contains the user details to verify the user.
     * @return {@link ResponseEntity} with the Jwt token for user and HTTP status Ok.
     */
    @PostMapping("login")
    public ResponseEntity<String> login(@Validated @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createSession(userDTO), HttpStatus.OK);
    }
}
