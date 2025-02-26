package com.example.springbootlearning.service;

import com.example.springbootlearning.domain.ExpenseRecord;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.repository.ExpenseRecordRepository;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExpenseRecordQueryService {
    private final ExpenseRecordRepository expenseRecordRepository;
    private final ExpenseCateroryQueryService expenseCateroryQueryService;
    private final EmployeeQueryService employeeQueryService;

    public ExpenseRecordQueryService(ExpenseCateroryQueryService expenseCateroryQueryService,
                                     EmployeeQueryService employeeQueryService,
                                     ExpenseRecordRepository expenseRecordRepository) {
        this.expenseCateroryQueryService = expenseCateroryQueryService;
        this.employeeQueryService = employeeQueryService;
        this.expenseRecordRepository = expenseRecordRepository;
    }

    public List<ExpenseRecord> getAllExpenses() {
        return expenseRecordRepository.getAllExpenseRecords();
    }

    public List<ExpenseRecord> getExpensesByEmployee(
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 18, fraction = 0, message = "ID must be an long and no more than 18 characters")
            long employeeId)
            throws EntityNotFoundException {
        employeeQueryService.getEmployeeById(employeeId);
        return expenseRecordRepository.getAllExpenseRecords()
                .stream()
                .filter(e -> e.getEmployee().getId() == employeeId)
                .collect(Collectors.toList());
    }

    public List<ExpenseRecord> getExpensesByCategory(String code) throws EntityNotFoundException {
        expenseCateroryQueryService.getCategoryByCode(code);
        return expenseRecordRepository.getAllExpenseRecords()
                .stream()
                .filter(e -> e.getCategory().getCode().equals(code))
                .collect(Collectors.toList());
    }

}
