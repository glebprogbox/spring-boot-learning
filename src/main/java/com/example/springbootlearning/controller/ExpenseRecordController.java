package com.example.springbootlearning.controller;

import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.domain.ExpenseRecord;
import com.example.springbootlearning.dto.ExpenseRecordRequestDto;
import com.example.springbootlearning.service.EmployeeQueryService;
import com.example.springbootlearning.service.ExpenseCateroryQueryService;
import com.example.springbootlearning.service.ExpenseRecordCommandService;
import com.example.springbootlearning.service.ExpenseRecordQueryService;
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
@RequestMapping("/api/v1/records")
@Validated
public class ExpenseRecordController {
    private final ExpenseRecordCommandService expenseRecordCommandService;
    private final ExpenseRecordQueryService expenseRecordQueryService;
    private final ExpenseCateroryQueryService expenseCateroryQueryService;
    private final EmployeeQueryService employeeQueryService;

    public ExpenseRecordController(ExpenseRecordCommandService expenseRecordCommandService,
                                   ExpenseCateroryQueryService expenseCateroryQueryService,
                                   EmployeeQueryService employeeQueryService,
                                   ExpenseRecordQueryService expenseRecordQueryService) {
        this.expenseRecordCommandService = expenseRecordCommandService;
        this.expenseCateroryQueryService = expenseCateroryQueryService;
        this.employeeQueryService = employeeQueryService;
        this.expenseRecordQueryService = expenseRecordQueryService;
    }

    @GetMapping()
    public List<ExpenseRecord> getAllExpenses() {
        return expenseRecordQueryService.getAllExpenses();
    }

    @GetMapping(path = "/searchByCategory/{code}")
    public List<ExpenseRecord> getExpensesByCategory(
            @PathVariable
            @NotBlank(message = "code cannot be blank")
            @Length(min = 1, max = 100, message = "100 or more characters are used for code")
            @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
            String code)
            throws EntityNotFoundException {
        return expenseRecordQueryService.getExpensesByCategory(code);
    }

    @GetMapping(path = "/searchByEmployee/{id}")
    public List<ExpenseRecord> getExpensesByEmployee(
            @PathVariable
            @PositiveOrZero(message = "id cannot be negative")
            @Digits(integer = 18, fraction = 0, message = "ID must be an long and no more than 18 characters")
            long id)
            throws EntityNotFoundException {
        return expenseRecordQueryService.getExpensesByEmployee(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ExpenseRecord addExpense(
            @RequestBody
            @Valid
            ExpenseRecordRequestDto expenseRecordRequestDto)
            throws EntityNotFoundException {
        ExpenseRecord expenseRecord = new ExpenseRecord(
                employeeQueryService.getEmployeeById(expenseRecordRequestDto.getId()),
                expenseCateroryQueryService.getCategoryByCode(expenseRecordRequestDto.getCode()),
                expenseRecordRequestDto.getAmount(),
                expenseRecordRequestDto.getDate(),
                expenseRecordRequestDto.getComment().orElse(null));
        return expenseRecordCommandService.addExpense(expenseRecord);
    }
}