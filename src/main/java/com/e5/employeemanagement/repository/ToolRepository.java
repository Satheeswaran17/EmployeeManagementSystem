package com.e5.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e5.employeemanagement.model.Tool;

/**
 * <p>
 * It is class to access, insert, update and delete the Tool in database.
 * </p>
 */
@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer> {

    boolean existsByNameAndVersion(String name, String version);

    Tool findByName(String name);
}
