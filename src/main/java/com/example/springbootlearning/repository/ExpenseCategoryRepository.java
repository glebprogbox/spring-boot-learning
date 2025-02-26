package com.example.springbootlearning.repository;

import com.example.springbootlearning.domain.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Data
public class ExpenseCategoryRepository {
    private final List<ExpenseCategory> expenseCategoryList = new ArrayList<>();

    public List<ExpenseCategory> getAllExpenseCategory() {
        return expenseCategoryList;
    }

    public void addExpenseCategory(ExpenseCategory expenseCategory) {
        expenseCategoryList.add(expenseCategory);
    }

    public void deleteExpenseCategory(ExpenseCategory expenseCategory) {
        expenseCategoryList.remove(expenseCategory);
    }
}
