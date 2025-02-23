package com.example.springbootlearning.service;

import com.example.springbootlearning.domain.Employee;
import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EmployeeService {
    private final List<Employee> employeeList = new ArrayList<>();

    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    public Employee createEmployee(Employee employee) throws EntityAlreadyExistsException {
        checkExistsEmployeeById(employee.getId());
        employeeList.add(employee);
        return employee;
    }

    public void deleteEmployeeById(long id) throws EntityNotFoundException {
        Employee employee = getEmployeeById(id);
        employeeList.remove(employee);
    }

    public Employee updateEmployeeById(String fullName, String position, long employeeId)
            throws EntityNotFoundException {
        Employee newEmployee = getEmployeeById(employeeId);
        if (fullName != null) {
            newEmployee.setFullName(fullName);
        }
        if (position != null) {
            newEmployee.setPosition(position);
        }
        return newEmployee;
    }

     public Employee getEmployeeById(long id) throws EntityNotFoundException {
        Employee employee = searchInListEmployeeById(id);
        if (searchInListEmployeeById(id) == null) {
            log.info("Employee with ID: {} not found", id);
            throw new EntityNotFoundException("Employee with ID: " + id + " not found", String.valueOf(id));
        }
        return employee;
    }

    void checkExistsEmployeeById(long id) throws EntityAlreadyExistsException {
        if (searchInListEmployeeById(id) != null) {
            log.info("Employee with ID: {} already exists", id);
            throw new EntityAlreadyExistsException("Employee with ID: " + id + " already exists", String.valueOf(id));
        }
    }

    Employee searchInListEmployeeById(long id) {
        return employeeList.stream()
                .filter(employee -> id == employee.getId())
                .findFirst()
                .orElse(null);
    }

}
