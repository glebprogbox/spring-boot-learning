package com.example.springbootlearning.exceptions;

import lombok.Getter;

@Getter
public class EmployeeAlreadyExistsException extends Exception {

    private final int employeeId;

    public EmployeeAlreadyExistsException(String message, int employeeId) {
        super(message);
        this.employeeId = employeeId;
    }
}
