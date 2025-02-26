package com.example.springbootlearning.service;

import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.domain.ExpenseRecord;
import com.example.springbootlearning.repository.ExpenseRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExpenseRecordCommandService {
    private final ExpenseCateroryQueryService expenseCateroryQueryService;
    private final EmployeeQueryService employeeQueryService;
    private final ExpenseRecordRepository expenseRecordRepository;

    public ExpenseRecordCommandService(ExpenseCateroryQueryService expenseCateroryQueryService,
                                       EmployeeQueryService employeeQueryService,
                                       ExpenseRecordRepository expenseRecordRepository) {
        this.expenseCateroryQueryService = expenseCateroryQueryService;
        this.employeeQueryService = employeeQueryService;
        this.expenseRecordRepository = expenseRecordRepository;
    }

    public ExpenseRecord addExpense(ExpenseRecord expenseRecord)
            throws EntityNotFoundException {
        employeeQueryService.getEmployeeById(expenseRecord.getEmployee().getId());
        expenseCateroryQueryService.getCategoryByCode(expenseRecord.getCategory().getCode());
        expenseRecordRepository.addExpenseRecord(expenseRecord);
        return expenseRecord;
    }

}
