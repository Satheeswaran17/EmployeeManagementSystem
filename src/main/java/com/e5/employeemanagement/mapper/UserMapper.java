package com.e5.employeemanagement.mapper;

import com.e5.employeemanagement.dto.UserDTO;
import com.e5.employeemanagement.model.Users;

/**
 * <p>
 * It is class Mapping DTO to Model and model to DTO for Users.
 * </p>
 */
public class UserMapper {

    /**
     * <p>
     * It is the method to map the users model to user dto using user dto builder.
     * </p>
     *
     * @param users {@link UserDTO} it contains user details to use a map model to dto.
     * @return {@link UserDTO} it contains restricted users details,
     *           such as username and password.
     */
    public static UserDTO userToDTO(Users users) {
        return UserDTO.builder()
                .userName(users.getUserName())
                .password(users.getPassword()).build();
    }

    /**
     * <p>
     * It is the method to map the user dto to users model using users builder.
     * </p>
     *
     * @param userDTO {@link UserDTO} it contains all details of user to use a map dto to model.
     * @return {@link Users}. it contains all details of the user.
     */
    public static Users dtoToUser(UserDTO userDTO) {
        return Users.builder()
                .id(userDTO.getId())
                .userName(userDTO.getUserName())
                .password(userDTO.getPassword()).build();

    }
}
