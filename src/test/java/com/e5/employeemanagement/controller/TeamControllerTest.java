package com.e5.employeemanagement.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.TeamDTO;
import com.e5.employeemanagement.service.TeamService;

@ExtendWith(MockitoExtension.class)
public class TeamControllerTest {
    @Mock
    private TeamService teamService;
    @InjectMocks
    private TeamController teamController;
    private TeamDTO teamDTO;
    private  int employeeId;

    @BeforeEach
    public void setUp() {
        teamDTO = TeamDTO.builder()
                .id(1)
                .domain("Development")
                .leadName("Sathees")
                .project("EmployeeMangaement")
                .build();
        employeeId = 1;
    }

    @Test
    public void testAddTeamSuccess() {
        when(teamService.addTeam(any(TeamDTO.class), anyInt())).thenReturn(teamDTO);
        ResponseEntity<TeamDTO> response = teamController.addTeam(teamDTO, employeeId);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(teamDTO, response.getBody());
    }

    @Test
    public void testGetTeamSuccess() {
        when(teamService.getTeamById(anyInt())).thenReturn(teamDTO);
        ResponseEntity<TeamDTO> response = teamController.getTeamById(employeeId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teamDTO, response.getBody());
    }

    @Test
    public void testUpdateTeamSuccess() {
        when(teamService.updateTeam(any(TeamDTO.class), anyInt())).thenReturn(teamDTO);
        ResponseEntity<TeamDTO> response = teamController.updateTeam(teamDTO, employeeId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teamDTO, response.getBody());
    }

    @Test
    public void testDeleteTeamSuccess() {
        doNothing().when(teamService).removeTeam(anyInt());
        ResponseEntity<HttpStatus> response = teamController.removeTeam(employeeId);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
