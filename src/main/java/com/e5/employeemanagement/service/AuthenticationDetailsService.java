package com.e5.employeemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.e5.employeemanagement.helper.AuthenticateException;
import com.e5.employeemanagement.model.Users;
import com.e5.employeemanagement.model.UserPrincipal;
import com.e5.employeemanagement.repository.UserRepository;

/**
 * <p>
 * It is Service class to UserDetails - related operations for spring security.
 * </p>
 */
@Service
public class AuthenticationDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * <p>
     *     it is the method to set details of user in UserDetails class for spring security.
     * </p>
     * @param userName it denotes the username in the form of email to fetch the user details.
     * @return {@link UserDetails} it contains the details of user.
     */
    @Override
    public UserDetails loadUserByUsername(String userName)  {
        try {
            Users users = userRepository.findByUserName(userName);
            if (users == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new UserPrincipal(users);
        } catch (UsernameNotFoundException e) {
            throw new AuthenticateException(e.getMessage());
        }
    }
}
