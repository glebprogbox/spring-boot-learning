package com.example.springbootlearning.controller;

import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.domain.ExpenseCategory;
import com.example.springbootlearning.service.ExpenseCategoryCommandService;
import com.example.springbootlearning.service.ExpenseCateroryQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class ExpenseCategoryController {
    private final ExpenseCategoryCommandService expenseCategoryCommandService;
    private final ExpenseCateroryQueryService expenseCateroryQueryService;

    public ExpenseCategoryController(ExpenseCategoryCommandService expenseCategoryCommandService,
                                     ExpenseCateroryQueryService expenseCateroryQueryService) {
        this.expenseCategoryCommandService = expenseCategoryCommandService;
        this.expenseCateroryQueryService = expenseCateroryQueryService;
    }

    @GetMapping()
    public List<ExpenseCategory> getAllCategories() {
        return expenseCateroryQueryService.getAllCategories();
    }

    @GetMapping(path = "/{code}")
    public ExpenseCategory getCategoryByCode(
            @PathVariable
            @NotBlank(message = "code cannot be blank")
            @Length(min = 1, max = 100, message = "100 or more characters are used for code")
            @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
            String code)
            throws EntityNotFoundException {
        return expenseCateroryQueryService.getCategoryByCode(code);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory createCategory(
            @RequestBody
            @Valid
            ExpenseCategory expenseCategory)
            throws EntityAlreadyExistsException {
        return expenseCategoryCommandService.createCategory(expenseCategory);
    }

    @DeleteMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoryByCode(
            @PathVariable
            @NotBlank(message = "code cannot be blank")
            @Length(min = 1, max = 100, message = "100 or more characters are used for code")
            @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
            String code)
            throws EntityNotFoundException {
        expenseCategoryCommandService.deleteCategoryByCode(code);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory updateCategoryByCode(
            @RequestBody
            @Valid
            ExpenseCategory expenseCategory)
            throws EntityNotFoundException {
        return expenseCategoryCommandService.updateCategoryByCode(
                expenseCategory.getCode(),
                expenseCategory.getDescription());
    }
}
