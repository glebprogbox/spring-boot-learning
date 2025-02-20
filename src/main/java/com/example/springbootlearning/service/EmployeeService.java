package com.example.springbootlearning.service;

import com.example.springbootlearning.exceptions.EmployeeAlreadyExistsException;
import com.example.springbootlearning.exceptions.EmployeeNotFoundException;
import com.example.springbootlearning.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EmployeeService {
    private static final List<Employee> employeeList = new ArrayList<>();

    public static List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeList);
    }

    public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
        return notFoundEmployeeByIdException(id);
    }

    public static Employee createEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        foundEmployeeByIdException(employee.getId());
        employeeList.add(employee);
        return employee;
    }

    public void deleteEmployeeById(int id) throws EmployeeNotFoundException {
        Employee employee = notFoundEmployeeByIdException(id);
        employeeList.remove(employee);
    }

    public Employee updateEmployeeById(String fullName, String position, int employeeId) throws EmployeeNotFoundException {
        Employee newEmployee = notFoundEmployeeByIdException(employeeId);
        if (fullName != null) {
            newEmployee.setFullName(fullName);
        }
        if (position != null) {
            newEmployee.setPosition(position);
        }
        return newEmployee;
    }

    static Employee notFoundEmployeeByIdException(int id) throws EmployeeNotFoundException {
        Employee employee = searchEmployeeById(id);
        if (employee == null) {
            log.info("Employee with ID: {} not found", id);
            throw new EmployeeNotFoundException("Employee with ID: " + id + " not found", id);
        }
        return employee;
    }

    static void foundEmployeeByIdException(int id) throws EmployeeAlreadyExistsException {
        if (searchEmployeeById(id) != null) {
            log.info("Employee with ID: {} already exists", id);
            throw new EmployeeAlreadyExistsException("Employee with ID: " + id + " already exists", id);
        }
    }

    static Employee searchEmployeeById(int id) {
        if (employeeList.isEmpty()) {
            return null;
        }
        return employeeList.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }

}
