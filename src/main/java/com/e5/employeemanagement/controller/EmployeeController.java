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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e5.employeemanagement.dto.EmployeeDTO;
import com.e5.employeemanagement.service.EmployeeService;

/**
 * <p>
 * It is class to handling request and response for Employee details.
 * </p>
 */
@RestController
@RequestMapping("v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     *<p>
     * Handles HTTP POST request to add a new employee using employee dto.
     * </p>
     *
     * @param employeeDTO {@link EmployeeDTO} it contains the employee details to be added.
     * @return {@link ResponseEntity} with the added employee details and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.addEmployee(employeeDTO), HttpStatus.CREATED);
    }

    /**
     *<p>
     * Handles HTTP GET request to get employee using employee id.
     * </p>
     *
     * @param id to get the employee, id comes from a path.
     * @return {@link ResponseEntity} with the fetched employee details and HTTP status OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    /**
     *<p>
     * Handles HTTP GET request to get all employees.
     * </p>
     *
     * @return {@link ResponseEntity} with the fetched list of employee's details and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(employeeService.getAllEmployees(page, size), HttpStatus.OK);
    }

    /**
     *<p>
     * Handles HTTP put request to update employee using employee dto.
     * </p>
     *
     * @param employeeDTO {@link EmployeeDTO} it contains the employee details to be update employee.
     * @return {@link ResponseEntity} with the updated employee details and HTTP status OK.
     */
    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.updateEmployee(employeeDTO),HttpStatus.OK);
    }

    /**
     *<p>
     * Handles HTTP delete request to delete employee using employee id.
     * </p>
     *
     * @param id to remove the employee, id comes from a path.
     * @return {@link ResponseEntity} HTTP status NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeEmployee(@PathVariable int id) {
        employeeService.removeEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
