package com.e5.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e5.employeemanagement.model.Team;

/**
 * <p>
 * It is class to access, insert, update and delete the Team in database.
 * </p>
 */
public interface TeamRepository extends JpaRepository<Team, Integer> {
    boolean existsByLeadName(String leadName);

    Team findByLeadName(String leadName);
}
