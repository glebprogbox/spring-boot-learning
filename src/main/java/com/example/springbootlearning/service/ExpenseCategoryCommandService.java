package com.example.springbootlearning.service;

import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.domain.ExpenseCategory;
import com.example.springbootlearning.repository.ExpenseCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExpenseCategoryCommandService {
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseCateroryQueryService expenseCateroryQueryService;
    public ExpenseCategoryCommandService(ExpenseCategoryRepository expenseCategoryRepository,
                                         ExpenseCateroryQueryService expenseCateroryQueryService) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseCateroryQueryService = expenseCateroryQueryService;
    }

    public ExpenseCategory createCategory(ExpenseCategory expenseCategory) throws EntityAlreadyExistsException {
        expenseCateroryQueryService.checkExistsCategoryByCode(expenseCategory.getCode());
        expenseCategoryRepository.addExpenseCategory(expenseCategory);
        return expenseCategory;
    }

    public void deleteCategoryByCode(String code) throws EntityNotFoundException {
        ExpenseCategory expenseCategory = expenseCateroryQueryService.getCategoryByCode(code);
        expenseCategoryRepository.deleteExpenseCategory(expenseCategory);
    }

    public ExpenseCategory updateCategoryByCode(String code, String description)
            throws EntityNotFoundException {
        ExpenseCategory expenseCategory = expenseCateroryQueryService.getCategoryByCode(code);
        expenseCategory.setDescription(description);
        return expenseCategory;
    }

}