package com.e5.employeemanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e5.employeemanagement.model.Employee;

/**
 * <p>
 * It is class to access, insert, update and delete the Employee in database.
 * </p>
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByIdAndIsDeletedFalse(int id);

    List<Employee> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsByPhoneNumberOrEmail(long phoneNumber, String email);
}


