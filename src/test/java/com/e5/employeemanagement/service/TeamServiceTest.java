package com.e5.employeemanagement.service;

import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.TeamDTO;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.model.Employee;
import com.e5.employeemanagement.model.Team;
import com.e5.employeemanagement.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    @Mock
    TeamRepository teamRepository;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private TeamService teamService;
    private Team team;
    private Employee employee;
    private TeamDTO teamDTO;

    @BeforeEach
    public void setUp() {
        team = Team.builder()
                .id(1)
                .domain("Development")
                .leadName("Sathees")
                .project("EmployeeMangaement")
                .build();
        teamDTO = TeamDTO.builder()
                .id(1)
                .domain("Development")
                .leadName("Sathees")
                .project("EmployeeMangaement")
                .build();
        employee = Employee.builder()
                .id(1)
                .name("Sathees")
                .email("sathees172003@gmail.com")
                .dob(new Date(2003, 8, 17))
                .phoneNumber(9345987431L)
                .role("Developer")
                .team(team)
                .build();
    }

    @Test
    public void testAddTeamSuccess() {
        employee.setTeam(null);
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        when(teamRepository.existsByLeadName(teamDTO.getLeadName())).thenReturn(false);
        doNothing().when(employeeService).saveEmployee(employee);
        TeamDTO addedTeam = teamService.addTeam(teamDTO, employee.getId());
        assertNotNull(addedTeam);
        assertEquals(addedTeam.getLeadName(), teamDTO.getLeadName());
    }

    @Test
    public void testAddTeamAlreadyExists() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(DuplicateKeyException.class, () -> teamService.addTeam(teamDTO, employee.getId()));
    }

    @Test
    public void testAddTeamNotFound() {
        when(employeeService.getEmployee(anyInt())).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> teamService.addTeam(teamDTO, employee.getId()));
    }

    @Test
    public void testAddTeaFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> teamService.addTeam(teamDTO, employee.getId()));
    }

    @Test
    public void testGetTeamSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertNotNull(teamService.getTeamById(employee.getId()));
    }

    @Test
    public void testGetTeamNotFound() {
        employee.setTeam(null);
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(NoSuchElementException.class, () -> teamService.getTeamById(employee.getId()));
    }

    @Test
    public void testGetTeamFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> teamService.getTeamById(employee.getId()));
    }

    @Test
    public void testUpdateTeamSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        when(teamRepository.existsByLeadName(teamDTO.getLeadName())).thenReturn(false);
        doNothing().when(employeeService).saveEmployee(employee);
        TeamDTO updatedTeam = teamService.updateTeam(teamDTO, employee.getId());
        assertNotNull(updatedTeam);
        assertEquals(updatedTeam.getLeadName(), teamDTO.getLeadName());
    }

    @Test
    public void testUpdateTeamNotFound() {
        employee.setTeam(null);
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(NoSuchElementException.class, () -> teamService.updateTeam(teamDTO, employee.getId()));
    }

    @Test
    public void testUpdateTeamFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> teamService.updateTeam(teamDTO, employee.getId()));
    }

    @Test
    public void testRemoveTeamSuccess() {
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        doNothing().when(employeeService).saveEmployee(employee);
        teamService.removeTeam(employee.getId());
    }

    @Test
    public void testRemoveTeamNotFound() {
        employee.setTeam(null);
        when(employeeService.getEmployee(anyInt())).thenReturn(employee);
        assertThrows(NoSuchElementException.class, () -> teamService.removeTeam(employee.getId()));
    }

    @Test
    public void testRemoveTeamFailure() {
        when(employeeService.getEmployee(anyInt())).thenThrow(EmployeeManagementException.class);
        assertThrows(EmployeeManagementException.class, () -> teamService.removeTeam(teamDTO.getId()));
    }
}
