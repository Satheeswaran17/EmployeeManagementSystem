package com.e5.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e5.employeemanagement.model.Laptop;

/**
 * <p>
 * It is class to access, insert, update and delete the Laptop in database.
 * </p>
 */
public interface LaptopRepository extends JpaRepository<Laptop, Integer> {
}
