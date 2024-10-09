package com.e5.employeemanagement.helper;

/**
 * <p>
 * It is class to handle the unauthorized Exception.
 * </p>
 */
public class AuthenticateException extends RuntimeException {
    public AuthenticateException(String message) {
        super(message);
    }

    public AuthenticateException(Throwable err) {
        super(err);
    }

    public AuthenticateException(String message, Throwable err) {
        super(message, err);
    }
}