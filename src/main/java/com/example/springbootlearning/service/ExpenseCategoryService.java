package com.example.springbootlearning.service;

import com.example.springbootlearning.exceptions.CategoryAlreadyExistsException;
import com.example.springbootlearning.exceptions.CategoryNotFoundException;
import com.example.springbootlearning.model.ExpenseCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExpenseCategoryService {
    private static final List<ExpenseCategory> expenseCategoryList = new ArrayList<>();

    public List<ExpenseCategory> getAllCategories() {
        return new ArrayList<>(expenseCategoryList);
    }

    public static ExpenseCategory getCategoryByCode(String code) throws CategoryNotFoundException {
        return notFoundCategoryByCodeException(code);
    }

    public ExpenseCategory createCategory(ExpenseCategory expenseCategory) throws CategoryAlreadyExistsException {
        foundCategoryByCodeException(expenseCategory.getCode());
        expenseCategoryList.add(expenseCategory);
        return expenseCategory;
    }

    public void deleteCategoryByCode(String code) throws CategoryNotFoundException {
        ExpenseCategory expenseCategory = notFoundCategoryByCodeException(code);
        expenseCategoryList.remove(expenseCategory);
    }

    public ExpenseCategory updateCategoryByCode(String code, String description)
            throws CategoryNotFoundException {
        ExpenseCategory expenseCategory = notFoundCategoryByCodeException(code);
        expenseCategory.setDescription(description);
        return expenseCategory;
    }

    void foundCategoryByCodeException(String code) throws CategoryAlreadyExistsException {
        if (searchCategoryByCode(code) != null) {
            log.info("Category with code {} already exists", code);
            throw new CategoryAlreadyExistsException("Category with code " + code + " already exists", code);
        }
    }

    static ExpenseCategory notFoundCategoryByCodeException(String code) throws CategoryNotFoundException {
        ExpenseCategory expenseCategory = searchCategoryByCode(code);
        if (searchCategoryByCode(code) == null) {
            log.info("Category with code {} not found", code);
            throw new CategoryNotFoundException("Category with code " + code + " not found", code);
        } else {
            return expenseCategory;
        }

    }

    static ExpenseCategory searchCategoryByCode(String code) {
        if (expenseCategoryList.isEmpty()) {
            return null;
        } else {
            return expenseCategoryList.stream().filter(expenseCategory -> expenseCategory
                    .getCode().equals(code)).findFirst().orElse(null);
        }
    }


}
