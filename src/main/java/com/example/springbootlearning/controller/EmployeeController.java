package com.example.springbootlearning.controller;

import com.example.springbootlearning.exceptions.EmployeeAlreadyExistsException;
import com.example.springbootlearning.exceptions.EmployeeNotFoundException;
import com.example.springbootlearning.model.Employee;
import com.example.springbootlearning.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = "application/json")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Employee getEmployeeById(
            @PathVariable
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 8, fraction = 0, message = "ID must be an integer and no more than 8 characters")
            int id)
            throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Employee createEmployee(
            @RequestBody
            @Valid
            Employee createEmployee)
            throws EmployeeAlreadyExistsException {
        Employee em = new Employee(createEmployee.getId(), createEmployee.getFullName(), createEmployee.getPosition());
        return employeeService.createEmployee(em);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployeeById(
            @PathVariable
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 8, fraction = 0, message = "ID must be an integer and no more than 8 characters")
            int id)
            throws EmployeeNotFoundException {
        employeeService.deleteEmployeeById(id);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployeeById(
            @RequestBody
            @Valid
            Employee updateEmployee)
            throws EmployeeNotFoundException {
        return employeeService.updateEmployeeById(
                updateEmployee.getFullName(),
                updateEmployee.getPosition(),
                updateEmployee.getId());
    }
}
