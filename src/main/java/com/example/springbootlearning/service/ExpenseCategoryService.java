package com.example.springbootlearning.service;

import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.domain.ExpenseCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExpenseCategoryService {
    private final List<ExpenseCategory> expenseCategoryList = new ArrayList<>();

    public List<ExpenseCategory> getAllCategories() {
        return expenseCategoryList;
    }

    public ExpenseCategory createCategory(ExpenseCategory expenseCategory) throws EntityAlreadyExistsException {
        checkExistsCategoryByCode(expenseCategory.getCode());
        expenseCategoryList.add(expenseCategory);
        return expenseCategory;
    }

    public void deleteCategoryByCode(String code) throws EntityNotFoundException {
        ExpenseCategory expenseCategory = getCategoryByCode(code);
        expenseCategoryList.remove(expenseCategory);
    }

    public ExpenseCategory updateCategoryByCode(String code, String description)
            throws EntityNotFoundException {
        ExpenseCategory expenseCategory = getCategoryByCode(code);
        expenseCategory.setDescription(description);
        return expenseCategory;
    }

    void checkExistsCategoryByCode(String code) throws EntityAlreadyExistsException {
        if (searchInListCategoryByCode(code) != null) {
            log.info("Category with code {} already exists", code);
            throw new EntityAlreadyExistsException("Category with code " + code + " already exists", code);
        }
    }

    public ExpenseCategory getCategoryByCode(String code) throws EntityNotFoundException {
        ExpenseCategory expenseCategory = searchInListCategoryByCode(code);
        if (searchInListCategoryByCode(code) == null) {
            log.info("Category with code {} not found", code);
            throw new EntityNotFoundException("Category with code " + code + " not found", code);
        } else {
            return expenseCategory;
        }
    }

    ExpenseCategory searchInListCategoryByCode(String code) {
        return expenseCategoryList.stream()
                .filter(expenseCategory -> expenseCategory.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}