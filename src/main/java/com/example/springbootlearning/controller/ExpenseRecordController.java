package com.example.springbootlearning.controller;

import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.domain.ExpenseRecord;
import com.example.springbootlearning.dto.ExpenseRecordRequestDto;
import com.example.springbootlearning.service.EmployeeCommandService;
import com.example.springbootlearning.service.ExpenseCommandCategoryService;
import com.example.springbootlearning.service.ExpenseCommandRecordService;
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
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/records")
@Validated
public class ExpenseRecordController {
    private final ExpenseCommandRecordService expenseCommandRecordService;
    private final ExpenseCommandCategoryService expenseCommandCategoryService;
    private final EmployeeCommandService employeeCommandService;

    public ExpenseRecordController(ExpenseCommandRecordService expenseCommandRecordService,
                                   ExpenseCommandCategoryService expenseCommandCategoryService,
                                   EmployeeCommandService employeeCommandService) {
        this.expenseCommandRecordService = expenseCommandRecordService;
        this.expenseCommandCategoryService = expenseCommandCategoryService;
        this.employeeCommandService = employeeCommandService;
    }

    @GetMapping()
    public List<ExpenseRecord> getAllExpenses() {
        return expenseCommandRecordService.getAllExpenses();
    }

    @GetMapping(path = "/searchByCategory/{code}")
    public List<ExpenseRecord> getExpensesByCategory(
            @PathVariable
            @NotBlank(message = "code cannot be blank")
            @Length(min = 1, max = 100, message = "100 or more characters are used for code")
            @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
            String code)
            throws EntityNotFoundException {
        return expenseCommandRecordService.getExpensesByCategory(code);
    }

    @GetMapping(path = "/searchByEmployee/{id}")
    public List<ExpenseRecord> getExpensesByEmployee(
            @PathVariable
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 18, fraction = 0, message = "ID must be an long and no more than 18 characters")
            long id)
            throws EntityNotFoundException {
        return expenseCommandRecordService.getExpensesByEmployee(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ExpenseRecord addExpense(
            @RequestBody
            @Valid
            ExpenseRecordRequestDto expenseRecordRequestDto)
            throws EntityNotFoundException {
        ExpenseRecord expenseRecord = new ExpenseRecord(
                employeeCommandService.getEmployeeById(expenseRecordRequestDto.getId()),
                expenseCommandCategoryService.getCategoryByCode(expenseRecordRequestDto.getCode()),
                expenseRecordRequestDto.getAmount(),
                expenseRecordRequestDto.getDate(),
                expenseRecordRequestDto.getComment().orElse(null));
        return expenseCommandRecordService.addExpense(expenseRecord);
    }
}