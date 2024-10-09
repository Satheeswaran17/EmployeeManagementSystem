package com.e5.employeemanagement.helper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * it is a global exception class to handle the exception.
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * <p>
     *     it is the method to handle the NoSuchElementException.
     * </p>
     *
     * @param noSuchElementException to handle the exception.
     * @return {@link ResponseEntity<String>} return the error message with HttpStatus NOT_FOUND.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        return new ResponseEntity<>(noSuchElementException.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * <p>
     *     it is the method to handle the DuplicateKeyException.
     * </p>
     *
     * @param duplicateKeyException to handle the exception.
     * @return {@link ResponseEntity<String>} return the error message with HttpStatus CONFLICT.
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicateElementException(DuplicateKeyException duplicateKeyException) {
        return new ResponseEntity<>(duplicateKeyException.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * <p>
     *     it is the method to handle the AuthenticateException.
     * </p>
     *
     * @param authenticateException to handle the exception.
     * @return {@link ResponseEntity<String>} return the error message with HttpStatus UNAUTHORIZED.
     */
    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity<String> handleAuthenticateException(AuthenticateException authenticateException) {
        return new ResponseEntity<>(authenticateException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * <p>
     *     it is the method to handle the EmployeeManagementException.
     * </p>
     *
     * @param employeeManagementException to handle the exception.
     * @return {@link ResponseEntity<String>} return the error message with HttpStatus INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(EmployeeManagementException.class)
    public ResponseEntity<String> handleEmployeeManagementException(
            EmployeeManagementException employeeManagementException) {
        return new ResponseEntity<>(employeeManagementException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * <p>
     *     it is the method to handle the MethodArgumentNotValidExceptions.
     * </p>
     *
     * @param methodArgumentNotValidException handle the exception.
     * @param request to get url of exception.
     * @return {@link ResponseEntity<Map>} return the error field,error message, url and time of the exception

hh     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(
            MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        errorMap.put("url", String.valueOf(request.getRequestURL()));
        errorMap.put("TimeStamp", String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
