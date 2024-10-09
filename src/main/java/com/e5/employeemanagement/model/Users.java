package com.e5.employeemanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * It is class represent the User.
 * </p>
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    private int id;
    private String userName;
    private String password;
}
