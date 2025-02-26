package com.example.springbootlearning.service;

import com.example.springbootlearning.domain.ExpenseCategory;
import com.example.springbootlearning.exceptions.EntityAlreadyExistsException;
import com.example.springbootlearning.exceptions.EntityNotFoundException;
import com.example.springbootlearning.repository.ExpenseCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExpenseCateroryQueryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCateroryQueryService(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public List<ExpenseCategory> getAllCategories() {
        return expenseCategoryRepository.getAllExpenseCategory();
    }

    void checkExistsCategoryByCode(String code) throws EntityAlreadyExistsException {
        if (searchInListCategoryByCode(code).isPresent()) {
            log.info("Category with code {} already exists", code);
            throw new EntityAlreadyExistsException("Category with code " + code + " already exists", code);
        }
    }

    public ExpenseCategory getCategoryByCode(String code) throws EntityNotFoundException {
        Optional<ExpenseCategory> expenseCategory = searchInListCategoryByCode(code);
        if (expenseCategory.isEmpty()) {
            log.info("Category with code {} not found", code);
            throw new EntityNotFoundException("Category with code " + code + " not found", code);
        } else {
            return expenseCategory.get();
        }
    }

    Optional<ExpenseCategory> searchInListCategoryByCode(String code) {
        return expenseCategoryRepository.getAllExpenseCategory().stream()
                .filter(expenseCategory -> expenseCategory.getCode().equals(code))
                .findFirst();
    }
}