package com.example.springbootlearning.controller;

import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.domain.ExpenseCategory;
import com.example.springbootlearning.service.ExpenseCategoryService;
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
    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping()
    public List<ExpenseCategory> getAllCategories() {
        return expenseCategoryService.getAllCategories();
    }

    @GetMapping(path = "/{code}")
    public ExpenseCategory getCategoryByCode(
            @PathVariable
            @NotBlank(message = "code cannot be blank")
            @Length(min = 1, max = 100, message = "100 or more characters are used for code")
            @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
            String code)
            throws EntityNotFoundException {
        return expenseCategoryService.getCategoryByCode(code);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory createCategory(
            @RequestBody
            @Valid
            ExpenseCategory expenseCategory)
            throws EntityAlreadyExistsException {
        return expenseCategoryService.createCategory(expenseCategory);
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
        expenseCategoryService.deleteCategoryByCode(code);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory updateCategoryByCode(
            @RequestBody
            @Valid
            ExpenseCategory expenseCategory)
            throws EntityNotFoundException {
        return expenseCategoryService.updateCategoryByCode(
                expenseCategory.getCode(),
                expenseCategory.getDescription());
    }
}
