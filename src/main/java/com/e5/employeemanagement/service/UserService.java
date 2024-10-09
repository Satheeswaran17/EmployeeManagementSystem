package com.e5.employeemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.e5.employeemanagement.helper.AuthenticateException;
import com.e5.employeemanagement.dto.UserDTO;
import com.e5.employeemanagement.mapper.UserMapper;
import com.e5.employeemanagement.model.Users;
import com.e5.employeemanagement.repository.UserRepository;
import com.e5.employeemanagement.util.JwtUtil;


/**
 * <p>
 * It is Service class to User - related operations.
 * </p>
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * <p>
     * It is method to save registered user details with encode the password of the user in the database.
     * </p>
     *
     * @param userDTO it contains the details of the user to store the details in a database.
     * @return {@link UserDTO} it return user details without unique id.
     */
    public UserDTO register(UserDTO userDTO) {
        Users users = UserMapper.dtoToUser(userDTO);
        users.setPassword(encoder.encode(users.getPassword()));
        return UserMapper.userToDTO(userRepository.save(users));
    }

    /**
     * <p>
     * It is method to verify the user and create token and session for user.
     * </p>
     *
     * @param userDTO it contains the details of the user to verify user.
     * @return {@link String} if the user is valid user then return token for the user.
     * @throws AuthenticateException if the username or password is invalid.
     */
    public String createSession(UserDTO userDTO) {
        try {
            Users users = UserMapper.dtoToUser(userDTO);
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUserName(),
                            users.getPassword()));
            return JwtUtil.generateToken(users.getUserName());
        } catch (BadCredentialsException e) {
            throw new AuthenticateException("Invalid username or password");
        }
    }
}
