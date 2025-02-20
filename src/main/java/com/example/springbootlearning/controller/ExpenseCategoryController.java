package com.example.springbootlearning.controller;

import com.example.springbootlearning.exceptions.CategoryAlreadyExistsException;
import com.example.springbootlearning.exceptions.CategoryNotFoundException;
import com.example.springbootlearning.model.ExpenseCategory;
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
@RequestMapping("api/v1/categories")
@Validated
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping(produces = "application/json")
    public List<ExpenseCategory> getAllCategories() {
        return expenseCategoryService.getAllCategories();
    }

    @GetMapping(path = "/{code}", produces = "application/json")
    public ExpenseCategory getCategoryByCode(
            @PathVariable
            @NotBlank(message = "code cannot be blank")
            @Length(min = 1, max = 100, message = "100 or more characters are used for code")
            @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
            String code)
            throws CategoryNotFoundException {
        return expenseCategoryService.getCategoryByCode(code);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory createCategory(
            @RequestBody
            @Valid
            ExpenseCategory expenseCategory)
            throws CategoryAlreadyExistsException {
        ExpenseCategory ec = new ExpenseCategory(expenseCategory.getCode(), expenseCategory.getDescription());
        return expenseCategoryService.createCategory(ec);
    }

    @DeleteMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoryByCode(
            @PathVariable
            @NotBlank(message = "code cannot be blank")
            @Length(min = 1, max = 100, message = "100 or more characters are used for code")
            @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
            String code)
            throws CategoryNotFoundException {
        expenseCategoryService.deleteCategoryByCode(code);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory updateCategoryByCode(
            @RequestBody
            @Valid
            ExpenseCategory expenseCategory)
            throws CategoryNotFoundException {
        return expenseCategoryService.updateCategoryByCode(
                expenseCategory.getCode(),
                expenseCategory.getDescription());
    }
}
