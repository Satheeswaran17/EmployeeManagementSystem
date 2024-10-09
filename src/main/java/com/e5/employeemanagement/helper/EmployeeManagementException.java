package com.e5.employeemanagement.helper;

/**
 * <p>
 * It is class to handle the database Exceptions.
 * </p>
 */
public class EmployeeManagementException extends RuntimeException {
    public EmployeeManagementException(String message) {
        super(message);
    }

    public EmployeeManagementException(Throwable err) {
        super(err);
    }

    public EmployeeManagementException(String message, Throwable err) {
        super(message, err);
    }
}