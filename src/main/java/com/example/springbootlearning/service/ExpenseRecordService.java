package com.example.springbootlearning.service;

import com.example.springbootlearning.exceptions.CategoryNotFoundException;
import com.example.springbootlearning.exceptions.EmployeeNotFoundException;
import com.example.springbootlearning.model.ExpenseRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExpenseRecordService {
    private final List<ExpenseRecord> expenseRecordList = new ArrayList<>();
    private final EmployeeService employeeService;
    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseRecordService(EmployeeService employeeService, ExpenseCategoryService expenseCategoryService) {
        this.employeeService = employeeService;
        this.expenseCategoryService = expenseCategoryService;
    }

    public List<ExpenseRecord> getAllExpenses() {
        return new ArrayList<>(expenseRecordList);
    }

    public ExpenseRecord addExpense(ExpenseRecord expenseRecord) throws EmployeeNotFoundException, CategoryNotFoundException {
        employeeService.notFoundEmployeeByIdException(expenseRecord.getEmployee().getId());
        expenseCategoryService.notFoundCategoryByCodeException(expenseRecord.getCategory().getCode());
        expenseRecordList.add(expenseRecord);
        return expenseRecord;
    }

    public List<ExpenseRecord> getExpensesByEmployee(int employeeId) throws EmployeeNotFoundException {
        EmployeeService.notFoundEmployeeByIdException(employeeId);
        return expenseRecordList.stream().filter(e -> e.getEmployee().getId() == employeeId).
                collect(Collectors.toList());
    }

    public List<ExpenseRecord> getExpensesByCategory(String code) throws CategoryNotFoundException {
        ExpenseCategoryService.notFoundCategoryByCodeException(code);
        return expenseRecordList.stream().filter(e->e.getCategory().getCode().equals(code)).
                collect(Collectors.toList());
    }

}
