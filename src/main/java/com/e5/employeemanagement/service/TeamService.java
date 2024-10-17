package com.e5.employeemanagement.service;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.e5.employeemanagement.dto.TeamDTO;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.mapper.TeamMapper;
import com.e5.employeemanagement.model.Employee;
import com.e5.employeemanagement.model.Team;
import com.e5.employeemanagement.repository.TeamRepository;

/**
 * <p>
 * It is Service class to Team - related operations.
 * </p>
 */
@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeService employeeService;
    private static final Logger logger = LogManager.getLogger(TeamService.class);

    /**
     * <p>
     * It is method to save the Team object in the database using team Dto and employee id.
     * </p>
     *
     * @param teamDTO {@link TeamDTO} it contains the all details of team except id.
     * @param  employeeId to bind the team with employee.
     * @return {@link TeamDTO} if team is successfully saved then return the inserted a team object,
     *             it contains the details of team.
     * @throws DuplicateKeyException if team already exist.
     * @throws NoSuchElementException if employee or team does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public TeamDTO addTeam(TeamDTO teamDTO, int employeeId) {
        try {
            logger.debug("Entering addTeam method");
            Employee employee = employeeService.getEmployee(employeeId);
            if (employee.getTeam() != null) {
                logger.warn("Team already exists for this Employee Id: {}", employeeId);
                throw new DuplicateKeyException("Team already exists for this Employee Id:" + employeeId);
            }
            Team team = TeamMapper.dtoToTeam(teamDTO);
            if(teamRepository.existsByLeadName(teamDTO.getLeadName())) {
                team = teamRepository.findByLeadName(teamDTO.getLeadName());
            }
            employee.setTeam(team);
            employeeService.saveEmployee(employee);
            logger.info("Team {} added successfully", team.getId());
            return TeamMapper.teamToDTO(team);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                logger.warn("Team already exists for this Employee id: {}", employeeId, e);
                throw e;
            } else if (e instanceof NoSuchElementException) {
                logger.warn("Employee Id: {} does not exist", employeeId, e);
                throw e;
            }
            logger.warn("Internal error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to fetch Team object from database using the employeeId.
     * </p>
     *
     * @param employeeId it denotes the unique id for employee to fetch the team.
     * @return {@link TeamDTO} if team already exists it return the fetched employee.
     * @throws NoSuchElementException if employee or team does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public TeamDTO getTeamById(int employeeId) {
        try {
            logger.debug("Entering getTeamById method");
            Employee employee = employeeService.getEmployee(employeeId);
            if (employee.getTeam() == null) {
                throw new NoSuchElementException("Team does not exist for this Employee Id:" + employeeId);
            }
            logger.info("Team {} retrieved successfully", employee.getId());
            return TeamMapper.teamToDTO(employee.getTeam());
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.warn(e.getMessage(), e);
                throw e;
            }
            logger.warn("Internal error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to update team object in database using team dto and employeeId.
     * </p>
     *
     * @param teamDTO it contains the details of the team to update the team.
     * @param employeeId it denotes the unique id for employee to bind the team with employee.
     * @return {@link TeamDTO} if team successfully updated it return updated team.
     * @throws NoSuchElementException if employee or team does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public TeamDTO updateTeam(TeamDTO teamDTO, int employeeId) {
        try {
            logger.debug("Entering updateTeam method");
            Employee employee = employeeService.getEmployee(employeeId);
            if (employee.getTeam() == null) {
                throw new NoSuchElementException("Employee id " + employeeId + " has no Team");
            }
            Team team = TeamMapper.dtoToTeam(teamDTO);
            if(teamRepository.existsByLeadName(teamDTO.getLeadName())) {
                team = teamRepository.findByLeadName(teamDTO.getLeadName());
            }
            employee.setTeam(team);
            employeeService.saveEmployee(employee);
            logger.info("Team {} updated successfully", team.getId());
            return TeamMapper.teamToDTO(team);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.warn(e.getMessage(), e);
                throw e;
            }
            logger.warn("Internal error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to remove Team object from specific employee using employeeId.
     * </p>
     *
     * @param employeeId to find the employee and remove the team from the employee.
     * @throws NoSuchElementException if employee or team does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public void removeTeam(int employeeId) {
        try {
            logger.debug("Entering removeTeam method");
            Employee employee = employeeService.getEmployee(employeeId);
            Team team = employee.getTeam();
            if (team == null) {
                throw new NoSuchElementException("Employee id " + employeeId + " has no Team");
            }
            employee.setTeam(null);
            logger.info("Team {} removed successfully", team.getId());
            employeeService.saveEmployee(employee);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.warn(e.getMessage(), e);
                throw e;
            }
            logger.warn("Internal error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }
}
