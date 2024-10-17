package com.e5.employeemanagement.repository;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e5.employeemanagement.model.Users;

/**
 * <p>
 * It is class to access, insert, update and delete the User in database.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUserName(String userName);

    boolean existsByUserName(String userName);
}
