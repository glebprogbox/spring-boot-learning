package com.example.springbootlearning.exceptions;

import lombok.Getter;

@Getter
public class EmployeeNotFoundException extends Exception {

    private final int employeeId;

    public EmployeeNotFoundException(String message, int employeeId) {
        super(message);
        this.employeeId = employeeId;
    }

}
