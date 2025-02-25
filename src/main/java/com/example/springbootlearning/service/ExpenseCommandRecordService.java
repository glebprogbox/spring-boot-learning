package com.example.springbootlearning.service;

import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.domain.ExpenseRecord;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExpenseCommandRecordService {
    private final List<ExpenseRecord> expenseRecordList = new ArrayList<>();
    private final EmployeeCommandService employeeCommandService;
    private final ExpenseCommandCategoryService expenseCommandCategoryService;

    public ExpenseCommandRecordService(EmployeeCommandService employeeCommandService,
                                       ExpenseCommandCategoryService expenseCommandCategoryService) {
        this.employeeCommandService = employeeCommandService;
        this.expenseCommandCategoryService = expenseCommandCategoryService;
    }

    public List<ExpenseRecord> getAllExpenses() {
        return expenseRecordList;
    }

    public ExpenseRecord addExpense(ExpenseRecord expenseRecord)
            throws EntityNotFoundException {
        employeeCommandService.getEmployeeById(expenseRecord.getEmployee().getId());
        expenseCommandCategoryService.getCategoryByCode(expenseRecord.getCategory().getCode());
        expenseRecordList.add(expenseRecord);
        return expenseRecord;
    }

    public List<ExpenseRecord> getExpensesByEmployee(
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 18, fraction = 0, message = "ID must be an long and no more than 18 characters")
            long employeeId)
            throws EntityNotFoundException {
        employeeCommandService.getEmployeeById(employeeId);
        return expenseRecordList.stream().filter(e -> e.getEmployee().getId() == employeeId).
                collect(Collectors.toList());
    }

    public List<ExpenseRecord> getExpensesByCategory(String code) throws EntityNotFoundException {
        expenseCommandCategoryService.getCategoryByCode(code);
        return expenseRecordList.stream()
                .filter(e -> e.getCategory().getCode().equals(code))
                .collect(Collectors.toList());
    }

}
