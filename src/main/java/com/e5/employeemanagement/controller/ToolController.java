package com.e5.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e5.employeemanagement.dto.ToolDTO;
import com.e5.employeemanagement.model.Tool;
import com.e5.employeemanagement.service.ToolService;

/**
 * <p>
 * It is class to handling request and response for Tool details.
 * </p>
 */
@RestController
@RequestMapping("v1/employees/{employeeId}/tools")
public class ToolController {
    @Autowired
    private ToolService toolService;

    /**
     *<p>
     * Handles HTTP POST request to add a new tool using tool dto and employee id.
     * </p>
     *
     * @param toolDTO {@link ToolDTO} it contains the tool details to be added.
     * @param employeeId to bind tool with employee, employee id comes from a path.
     * @return {@link ResponseEntity} with the added team details and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<ToolDTO> addTool(@Validated  @RequestBody ToolDTO toolDTO, @PathVariable int employeeId) {
        return new ResponseEntity<>(toolService.addTool(toolDTO, employeeId), HttpStatus.CREATED);
    }

    /**
     *<p>
     * Handles HTTP GET request to get tool using employee id.
     * </p>
     *
     * @param employeeId to get the tool for specific employee, employeeId comes from a path.
     * @return {@link ResponseEntity} with the fetched list of tools details for specific employee and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<Tool>> getToolById(@PathVariable int employeeId) {
        return new ResponseEntity<>(toolService.getToolById(employeeId), HttpStatus.OK);
    }

    /**
     *<p>
     * Handles HTTP delete request to delete tool using employee id.
     * </p>
     *
     * @param employeeId to remove the tools for specific employee, employeeId comes from a path.
     * @return {@link ResponseEntity} HTTP status NO_CONTENT.
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> removeTool(@PathVariable int employeeId) {
        toolService.removeTool(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
