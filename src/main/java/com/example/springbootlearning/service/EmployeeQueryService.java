package com.example.springbootlearning.service;

import com.example.springbootlearning.domain.Employee;
import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeQueryService {
    private final EmployeeRepository employeeRepository;

    public EmployeeQueryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }


    public Employee getEmployeeById(long id) throws EntityNotFoundException {
        Optional<Employee> employee = searchInListEmployeeById(id);
        if (employee.isEmpty()) {
            log.info("Employee with ID: {} not found", id);
            throw new EntityNotFoundException("Employee with ID: " + id + " not found", String.valueOf(id));
        }
        return employee.get();
    }

    void checkExistsEmployeeById(long id) throws EntityAlreadyExistsException {
        if (searchInListEmployeeById(id).isPresent()) {
            log.info("Employee with ID: {} already exists", id);
            throw new EntityAlreadyExistsException("Employee with ID: " + id + " already exists", String.valueOf(id));
        }
    }

    Optional<Employee> searchInListEmployeeById(long id) {
        return employeeRepository.getEmployeeList().stream()
                .filter(employee -> Long.valueOf(id).equals(employee.getId()))
                .findFirst();
    }

}
