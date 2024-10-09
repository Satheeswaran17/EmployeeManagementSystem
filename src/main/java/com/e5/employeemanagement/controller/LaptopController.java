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

import com.e5.employeemanagement.dto.LaptopDTO;
import com.e5.employeemanagement.service.LaptopService;

/**
 * <p>
 * It is class to handling request and response for Laptop details.
 * </p>
 */
@RestController
@RequestMapping("v1/employees/{employeeId}/laptops")
public class LaptopController {
    @Autowired
    private LaptopService laptopService;

    /**
     *<p>
     * Handles HTTP POST request to add a new laptop using laptop dto and employee id.
     * </p>
     *
     * @param laptopDTO {@link LaptopDTO} it contains the laptop details to be added.
     * @param employeeId to bind the laptop with employee, employeeId is the path variable.
     * @return {@link ResponseEntity} with the added laptop details and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<LaptopDTO> addLaptop(@Validated @RequestBody LaptopDTO laptopDTO,
                                               @PathVariable int employeeId) {
        return new ResponseEntity<>(laptopService.addLaptop(laptopDTO, employeeId),HttpStatus.CREATED);
    }

    /**
     *<p>
     * Handles HTTP GET request to get laptop using employee id.
     * </p>
     *
     * @param employeeId to get the laptop for a specific employee, employeeId comes from a path.
     * @return {@link ResponseEntity} with the fetched laptop details and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<LaptopDTO> getLaptopById(@PathVariable int employeeId) {
        return new ResponseEntity<>(laptopService.getLaptopById(employeeId), HttpStatus.OK);
    }

    /**
     *<p>
     * Handles HTTP put request to update laptop for specific employee using laptop dto and employee id.
     * </p>
     *
     * @param laptopDTO {@link LaptopDTO} it contains the laptop details to be update laptop.
     * @param employeeId to bind a laptop with employee, employee id comes from a path.
     * @return {@link ResponseEntity} with the updated laptop details and HTTP status OK.
     */
    @PutMapping
    public ResponseEntity<LaptopDTO> updateLaptop(@Validated @RequestBody LaptopDTO laptopDTO,
                                                  @PathVariable int employeeId) {
        return new ResponseEntity<>(laptopService.updateLaptop(laptopDTO, employeeId),HttpStatus.OK);
    }

    /**
     *<p>
     * Handles HTTP delete request to delete laptop for the specific employee using employee id.
     * </p>
     *
     * @param employeeId to remove the Laptop, employeeId comes from a path.
     * @return {@link ResponseEntity} with HTTP status NO_CONTENT.
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> removeLaptop(@PathVariable int employeeId) {
        laptopService.removeLaptop(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
