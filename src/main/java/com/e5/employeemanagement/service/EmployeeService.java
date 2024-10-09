package com.e5.employeemanagement.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.e5.employeemanagement.dto.EmployeeDTO;
import com.e5.employeemanagement.helper.EmployeeManagementException;
import com.e5.employeemanagement.mapper.EmployeeMapper;
import com.e5.employeemanagement.model.Employee;
import com.e5.employeemanagement.repository.EmployeeRepository;

/**
 * <p>
 * It is Service class to Employee - related operations.
 * </p>
 */
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    /**
     * <p>
     * It is method to save the Employee object in the database using employee Dto.
     * </p>
     *
     * @param employeeDTO {@link EmployeeDTO} it contains the all details of employee except id.
     * @return {@link EmployeeDTO} if employee is successfully saved then return the inserted an employee object,
     *             it contains the details of employee with id.
     * @throws DuplicateKeyException if the email or phone number already exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        try {
            logger.debug("Entering addEmployee method");
            if (employeeRepository.existsByPhoneNumberOrEmailAndIsDeletedFalse(employeeDTO.getPhoneNumber(),
                    employeeDTO.getEmail())) {
                throw new DuplicateKeyException("Employee phone number " + employeeDTO.getPhoneNumber()
                        + " Already present ");
            }
            Employee employee = EmployeeMapper.dtoToEmployee(employeeDTO);
            logger.info("Employee id {} Added ", employeeDTO.getId());
            return EmployeeMapper.employeeToDTO(employeeRepository.save(employee));
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException) {
                logger.error("Employee phone number {} already exists ", employeeDTO.getPhoneNumber(), e);
                throw new DuplicateKeyException(e.getMessage());
            }
            logger.error("Issue with server ", e);
            throw new EmployeeManagementException("Issue with server ");
        }
    }

    /**
     * <p>
     * It is method to fetch Employee object from database using the id.
     * </p>
     *
     * @param id it denotes the unique id for employee to fetch the employee.
     * @return {@link EmployeeDTO} if employee already exists it return the fetched employee.
     * @throws NoSuchElementException if employee does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public EmployeeDTO getEmployeeById(int id) {
        try {
            logger.debug("Entering getEmployeeById method");
            Employee employee = getEmployee(id);
            logger.info("Employee id {} Retrieved successfully", id);
            return EmployeeMapper.employeeToReturnDTO(employee);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.error("Employee id {} does not exist ", id, e);
                throw new NoSuchElementException(e.getMessage());
            }
            logger.error("Issue with server ", e);
            throw new EmployeeManagementException("Issue with server ");
        }
    }

    /**
     * <p>
     * It is method to fetch all Employees object from the database in the order of paging.
     * </p>
     *
     * @param page page number to get the employee.
     * @param size number of employees in one page.
     * @return {@link List<EmployeeDTO>} return all employees in the form pagination.
     * @throws NoSuchElementException if employee does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public List<EmployeeDTO> getAllEmployees(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            logger.debug("Entering getAllEmployees method");
            List<Employee> employees = employeeRepository.findAllByIsDeletedFalse(pageable);
            logger.info("Employees Retrieved successfully ");
            return employees.stream().map(EmployeeMapper::employeeToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Issue with server ", e);
            throw new EmployeeManagementException("Issue with server ");
        }
    }

    /**
     * <p>
     * It is method to update Employee object in database using employee dto.
     * </p>
     *
     * @param employeeDTO it contains the details of employee with id.
     * @return {@link EmployeeDTO} if the employee is successfully updated it return employee dto.
     * @throws NoSuchElementException if employee does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        try {
            logger.debug("Entering updateEmployee method");
            if (!employeeRepository.existsById(employeeDTO.getId())) {
                throw new NoSuchElementException("Employee id " + employeeDTO.getId() + " not exists ");
            }
            logger.info("Employee id {} Updated successfully", employeeDTO.getId());
            return EmployeeMapper.employeeToDTO(employeeRepository.save(EmployeeMapper.dtoToEmployee(employeeDTO)));
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.error("Employee id {} not exists ", employeeDTO.getId(), e);
                throw  new NoSuchElementException(e.getMessage());
            }
            logger.error("Issue with server ", e);
            throw new EmployeeManagementException("Issue with server ");
        }
    }

    /**
     * <p>
     * It is method to remove Employee object in the database using id.
     * </p>
     *
     * @param id to find the employee.
     * @throws NoSuchElementException if employee does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public void removeEmployee(int id) {
        try {
            logger.debug("Entering removeEmployee method");
            Employee employee = getEmployee(id);
            employee.setDeleted(true);
            if (employee.getLaptop() != null) {
                employee.getLaptop().setDeleted(false);
            }
            logger.info("Employee id {} Removed successfully", id);
            employeeRepository.save(employee);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.error("Employee id: {} not exist ", id, e);
                throw new NoSuchElementException(e.getMessage());
            }
            logger.error("Issue with server ", e);
            throw new EmployeeManagementException("Issue with server ");
        }
    }

    /**
     * <p>
     * It is method to fetch Employee object from database using employeeId.
     * </p>
     *
     * @param employeeId to find the Employee details.
     * @return {@link Employee} if the employee already exists.
     * @throws NoSuchElementException if employee does not exist.
     * @throws EmployeeManagementException if any issue with server.
     */
    public Employee getEmployee(int employeeId) {
        try {
            logger.debug("Entering getEmployee method");
            Employee employee = employeeRepository.findByIdAndIsDeletedFalse(employeeId);
            if (employee == null) {
                throw new NoSuchElementException("No Active Employee in DataBase with ID " + employeeId);
            }
            logger.info("Employee Id {} Retrieved successfully", employeeId);
            return employee;
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                logger.error("Employee id : {} does not exist ", employeeId, e);
                throw new NoSuchElementException(e.getMessage());
            }
            logger.error("Issue with server ", e);
            throw new EmployeeManagementException("Internal server error");
        }
    }

    /**
     * <p>
     * It is method to save Employee object using employee object in the database.
     * </p>
     *
     * @param employee it contains an employee object to save the employee.
     * @throws EmployeeManagementException if any issue with server.
     */
    public void saveEmployee(Employee employee) {
        try {
            employeeRepository.save(employee);
        } catch (Exception e) {
            logger.warn("Issue with server ", e);
            throw new EmployeeManagementException("Issue with server ");
        }
    }
}

