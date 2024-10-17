package com.e5.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e5.employeemanagement.dto.TeamDTO;
import com.e5.employeemanagement.model.Team;
import com.e5.employeemanagement.service.TeamService;

/**
 * <p>
 * It is class to handling request and response for Team details.
 * </p>
 */
@RestController
@RequestMapping("v1/employees/{employeeId}/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    /**
     *<p>
     * Handles HTTP POST request to add a new Team using team dto and employee id.
     * </p>
     *
     * @param teamDTO {@link TeamDTO} it contains the team details to be added.
     * @param employeeId to bind team with employee, employee id comes from a path.
     * @return {@link ResponseEntity} with the added team details and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<TeamDTO> addTeam(@Validated @RequestBody TeamDTO teamDTO, @PathVariable int employeeId) {
        return new ResponseEntity<>(teamService.addTeam(teamDTO, employeeId), HttpStatus.CREATED);
    }

    /**
     *<p>
     * Handles HTTP GET request to get team using employee id.
     * </p>
     *
     * @param employeeId to get the team for specific employee, id comes from a path.
     * @return {@link ResponseEntity} with the fetched team details and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable int employeeId) {
        return new ResponseEntity<>(teamService.getTeamById(employeeId), HttpStatus.OK);
    }

    /**
     *<p>
     * Handles HTTP put request to update team using team dto and employee id.
     * </p>
     *
     * @param teamDTO {@link TeamDTO} it contains the team details to be update team.
     * @param employeeId to bind the team with employee, employee id comes from a path.
     * @return {@link ResponseEntity} with the updated team details and HTTP status OK.
     */
    @PutMapping
    public ResponseEntity<TeamDTO> updateTeam(@Validated @RequestBody TeamDTO teamDTO , @PathVariable int employeeId) {
        return new ResponseEntity<>(teamService.updateTeam(teamDTO, employeeId),HttpStatus.OK);
    }

    /**
     *<p>
     * Handles HTTP delete request to delete team using employee id.
     * </p>
     *
     * @param employeeId to remove the team for specific employee, id comes from a path.
     * @return {@link ResponseEntity} HTTP status NO_CONTENT.
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> removeTeam(@PathVariable int employeeId) {
        teamService.removeTeam(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
