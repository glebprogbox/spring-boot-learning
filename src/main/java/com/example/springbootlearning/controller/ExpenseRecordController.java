package com.example.springbootlearning.controller;

import com.example.springbootlearning.exceptions.CategoryNotFoundException;
import com.example.springbootlearning.exceptions.EmployeeNotFoundException;
import com.example.springbootlearning.model.ExpenseRecord;
import com.example.springbootlearning.model.ExpenseRecordRequestDto;
import com.example.springbootlearning.service.EmployeeService;
import com.example.springbootlearning.service.ExpenseCategoryService;
import com.example.springbootlearning.service.ExpenseRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/records")
@Validated
public class ExpenseRecordController {
    private final ExpenseRecordService expenseRecordService;
    private final ExpenseCategoryService expenseCategoryService;
    private final EmployeeService employeeService;

    public ExpenseRecordController(ExpenseRecordService expenseRecordService) {
        this.expenseRecordService = expenseRecordService;
        this.expenseCategoryService = new ExpenseCategoryService();
        this.employeeService = new EmployeeService();
    }

    @GetMapping(produces = "application/json")
    public List<ExpenseRecord> getAllExpenses() {
        return expenseRecordService.getAllExpenses();
    }

    @GetMapping(path = "/searchByCategory/{code}", produces = "application/json")
    public List<ExpenseRecord> getExpensesByCategory(
            @PathVariable
            @NotBlank(message = "code cannot be blank")
            @Length(min = 1, max = 100, message = "100 or more characters are used for code")
            @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
            String code)
            throws CategoryNotFoundException {
        return expenseRecordService.getExpensesByCategory(code);
    }

    @GetMapping(path = "/searchByEmployee/{id}", produces = "application/json")
    public List<ExpenseRecord> getExpensesByEmployee(
            @PathVariable
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 8, fraction = 0, message = "ID must be an integer and no more than 8 characters")
            int id)
            throws EmployeeNotFoundException {
        return expenseRecordService.getExpensesByEmployee(id);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseRecord addExpense(
            @RequestBody
            @Valid
            ExpenseRecordRequestDto expenseRecordRequestDto)
            throws EmployeeNotFoundException, CategoryNotFoundException {
        ExpenseRecord er = new ExpenseRecord(
                employeeService.getEmployeeById(expenseRecordRequestDto.getId()),
                expenseCategoryService.getCategoryByCode(expenseRecordRequestDto.getCode()),
                expenseRecordRequestDto.getAmount(),
                expenseRecordRequestDto.getDate(),
                expenseRecordRequestDto.getComment());
        return expenseRecordService.addExpense(er);
    }
}