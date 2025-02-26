package com.example.springbootlearning.controller;

import com.example.springbootlearning.domain.Employee;
import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.service.EmployeeCommandService;
import com.example.springbootlearning.service.EmployeeQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Validated
public class EmployeeController {
    private final EmployeeCommandService employeeCommandService;
    private final EmployeeQueryService employeeQueryService;

    public EmployeeController(EmployeeCommandService employeeCommandService, EmployeeQueryService employeeQueryService) {
        this.employeeCommandService = employeeCommandService;
        this.employeeQueryService = employeeQueryService;
    }

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return employeeQueryService.getAllEmployees();
    }

    @GetMapping(path = "/{id}")
    public Employee getEmployeeById(
            @PathVariable
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 18, fraction = 0, message = "ID must be an long and no more than 18 characters")
            long id)
            throws EntityNotFoundException {
        return employeeQueryService.getEmployeeById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Employee createEmployee(
            @RequestBody
            @Valid
            Employee createEmployee)
            throws EntityAlreadyExistsException {
        return employeeCommandService.createEmployee(createEmployee);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployeeById(
            @PathVariable
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 18, fraction = 0, message = "ID must be an long and no more than 18 characters")
            long id)
            throws EntityNotFoundException {
        employeeCommandService.deleteEmployeeById(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployeeById(
            @RequestBody
            @Valid
            Employee updateEmployee)
            throws EntityNotFoundException {
        return employeeCommandService.updateEmployeeById(
                updateEmployee.getFullName(),
                updateEmployee.getPosition(),
                updateEmployee.getId());
    }
}
