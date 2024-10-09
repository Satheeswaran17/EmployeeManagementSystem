package com.e5.employeemanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e5.employeemanagement.dto.ToolDTO;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.mapper.ToolMapper;
import com.e5.employeemanagement.model.Employee;
import com.e5.employeemanagement.model.Tool;
import com.e5.employeemanagement.repository.ToolRepository;


/**
 * <p>
 * It is Service class to Tool - related operations.
 * </p>
 */
@Service
public class ToolService {
    @Autowired
    private ToolRepository toolRepository;
    @Autowired
    private EmployeeService employeeService;
    private static final Logger logger = LogManager.getLogger(ToolService.class);

    /**
     * <p>
     * It is method to save the Tool object in the database using tool Dto and employeeId.
     * </p>
     *
     * @param toolDTO {@link ToolDTO} it contains the all details of tool except id.
     * @param employeeId to bind tool with employee.
     * @return {@link ToolDTO} if tool is successfully saved then return the inserted a tool object,
     *             it contains the details of tool.
     * @throws NoSuchElementException if employee does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public ToolDTO addTool(ToolDTO toolDTO, int employeeId) {
        try {
            Employee employee = employeeService.getEmployee(employeeId);
            Tool tool = ToolMapper.dtoToTool(toolDTO);
            if (toolRepository.existsByNameAndVersion(toolDTO.getName(), toolDTO.getVersion())) {
                tool = toolRepository.findByName(toolDTO.getName());
            }
            employee.getTools().add(tool);
            employeeService.saveEmployee(employee);
            return ToolMapper.toolToDTO(tool);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.error(e.getMessage(), e);
                throw  new NoSuchElementException(e.getMessage());
            }
            logger.warn("Internal server error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to fetch Tool object from database using the employeeId.
     * </p>
     *
     * @param employeeId it denotes the unique id for employee to fetch the tool.
     * @return {@link List<ToolDTO>} if tools already exists for specific employee it returns the fetched tools.
     * @throws NoSuchElementException if employee does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public List<Tool> getToolById(int employeeId) {
        try {
            logger.debug("Entering getToolById method");
            Employee employee = employeeService.getEmployee(employeeId);
            if (employee.getTools().isEmpty()) {
                throw new NoSuchElementException("No tools found for employee id " + employeeId);
            }
            return employee.getTools();
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.error(e.getMessage(), e);
                throw  new NoSuchElementException(e.getMessage());
            }
            logger.warn("Internal server error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to remove Tool object from the specific employee using using employeeId.
     * </p>
     *
     * @param employeeId to find the employee and remove tools from the employee.
     * @throws NoSuchElementException if employee or tools does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public void removeTool(int employeeId) {
        try {
            logger.debug("Entering removeTool method");
            Employee employee = employeeService.getEmployee(employeeId);
            if (employee.getTools().isEmpty()) {
                throw new NoSuchElementException("No tools found for employee id " + employeeId);
            }
            employee.setTools(new ArrayList<>());
            employeeService.saveEmployee(employee);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.error(e.getMessage(), e);
                throw  new NoSuchElementException(e.getMessage());
            }
            logger.warn("Internal server error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }
}
