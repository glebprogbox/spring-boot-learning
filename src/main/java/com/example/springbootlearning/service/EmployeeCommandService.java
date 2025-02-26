package com.example.springbootlearning.service;

import com.example.springbootlearning.domain.Employee;
import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.repository.EmployeeRepository;
import org.springframework.stereotype.Service;


@Service
public class EmployeeCommandService {
    private final EmployeeQueryService employeeQueryService;
    private final EmployeeRepository employeeRepository;

    public EmployeeCommandService(EmployeeQueryService employeeQueryService, EmployeeRepository employeeRepository) {
        this.employeeQueryService = employeeQueryService;
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) throws EntityAlreadyExistsException {
        employeeQueryService.checkExistsEmployeeById(employee.getId());
        employeeRepository.addEmployee(employee);
        return employee;
    }

    public void deleteEmployeeById(long id) throws EntityNotFoundException {
        Employee employee = employeeQueryService.getEmployeeById(id);
        employeeRepository.removeEmployee(employee);
    }

    public Employee updateEmployeeById(String fullName, String position, long employeeId)
            throws EntityNotFoundException {
        Employee newEmployee = employeeQueryService.getEmployeeById(employeeId);
        newEmployee.setFullName(fullName != null ? fullName : newEmployee.getFullName());
        newEmployee.setPosition(position != null ? position : newEmployee.getPosition());
        return newEmployee;
    }


}
