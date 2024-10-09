package com.e5.employeemanagement.service;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.e5.employeemanagement.repository.LaptopRepository;
import com.e5.employeemanagement.dto.LaptopDTO;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.mapper.LaptopMapper;
import com.e5.employeemanagement.model.Employee;
import com.e5.employeemanagement.model.Laptop;

/**
 * <p>
 * It is Service class to Laptop - related operations.
 * </p>
 */
@Service
public class LaptopService {
    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private EmployeeService employeeService;
    private static final Logger logger = LogManager.getLogger(LaptopService.class);

    /**
     * <p>
     * It is method to save the Laptop object in the database using laptop Dto.
     * </p>
     *
     * @param laptopDTO {@link LaptopDTO} it contains the all details of laptop except id.
     * @param employeeId to bind the laptop with employee.
     * @return {@link LaptopDTO} if laptop is successfully saved then return the inserted a laptop object, 
     *             it contains the details of laptop without id.
     * @throws DuplicateKeyException if already laptop is existed.
     * @throws NoSuchElementException if a laptop or employee does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public LaptopDTO addLaptop(LaptopDTO laptopDTO, int employeeId) {
        try {
            logger.debug("Entering addLaptop method");
            Employee employee = employeeService.getEmployee(employeeId);
            if (employee.getLaptop() != null) {
                logger.warn("Laptop already exists for this Employee id {}", employeeId);
                throw new DuplicateKeyException("Laptop already exists for this Employee id " + employeeId);
            }
            Laptop laptop = LaptopMapper.dtoToLaptop(laptopDTO);
            employee.setLaptop(laptop);
            employeeService.saveEmployee(employee);
            logger.info("Laptop {} added successfully", laptop.getId());
            return LaptopMapper.laptopToDTO(laptop);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                logger.warn("Laptop already exist for this Employee id {} ", employeeId, e);
                throw new DuplicateKeyException(e.getMessage());
            } else if (e instanceof NoSuchElementException) {
                logger.warn("Employee id {} does not exist", employeeId, e);
                throw new NoSuchElementException(e.getMessage());
            }
            logger.warn("Internal server error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to fetch Laptop object from database using the employeeId.
     * </p>
     *
     * @param employeeId it denotes the unique id for employee to fetch the laptop.
     * @return {@link LaptopDTO} if laptop already exists it return the fetched laptop.
     * @throws NoSuchElementException if employee or laptop does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public LaptopDTO getLaptopById(int employeeId) {
        try {
            logger.debug("Entering getLaptopById method");
            Employee employee = employeeService.getEmployee(employeeId);
            Laptop laptop = employee.getLaptop();
            if (laptop == null) {
                throw new NoSuchElementException("Employee id " + employeeId + " has no Laptop");
            }
            logger.info("Laptop {} retrieved successfully", laptop.getId());
            return LaptopMapper.laptopToDTO(employee.getLaptop());
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.warn(e.getMessage(), e);
                throw new NoSuchElementException(e.getMessage());
            }
            logger.warn("Internal server error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to update laptop object in database using laptop dto and employeeId.
     * </p>
     *
     * @param laptopDTO it contains the details of the laptop to update the laptop.
     * @param employeeId it denotes the unique id for employee to bind the laptop with employee.
     * @return {@link LaptopDTO} if laptop successfully updated it return updated laptop.
     * @throws NoSuchElementException if employee or laptop does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public LaptopDTO updateLaptop(LaptopDTO laptopDTO, int employeeId) {
        try {
            logger.debug("Entering updateLaptop method");
            Employee employee = employeeService.getEmployee(employeeId);
            Laptop laptop = employee.getLaptop();
            if (laptop == null) {
                throw new NoSuchElementException("Employee id " + employeeId + " has no Laptop");
            }
            employee.setLaptop(LaptopMapper.dtoToLaptop(laptopDTO));
            employeeService.saveEmployee(employee);
            logger.info("Laptop {} updated successfully", laptop.getId());
            return LaptopMapper.laptopToDTO(employee.getLaptop());
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.warn(e.getMessage(), e);
                throw new NoSuchElementException(e.getMessage());
            }
            logger.warn("Internal server error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to remove laptop object in the database using employeeId.
     * </p>
     *
     * @param employeeId to find the Laptop.
     * @throws NoSuchElementException if employee or laptop does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public void removeLaptop(int employeeId) {
        try {
            Employee employee = employeeService.getEmployee(employeeId);
            Laptop laptop = employee.getLaptop();
            if (laptop == null) {
                throw new NoSuchElementException("Employee id " + employeeId + " has no Laptop");
            }
            laptop.setDeleted(true);
            employee.setLaptop(null);
            employeeService.saveEmployee(employee);
            laptopRepository.save(laptop);
            logger.info("Laptop {} removed successfully", laptop.getId());
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.warn(e.getMessage(), e);
                throw new NoSuchElementException(e.getMessage());
            }
            logger.warn("Internal server error", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }
}
