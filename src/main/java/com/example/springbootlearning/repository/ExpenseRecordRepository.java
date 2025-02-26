package com.example.springbootlearning.repository;

import com.example.springbootlearning.domain.ExpenseRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Data
public class ExpenseRecordRepository {
    private final List<ExpenseRecord> expenseRecordList = new ArrayList<>();

    public List<ExpenseRecord> getAllExpenseRecords() {
        return expenseRecordList;
    }

    public void addExpenseRecord(ExpenseRecord expenseRecord) {
        expenseRecordList.add(expenseRecord);
    }

    public void deleteExpenseRecord(ExpenseRecord expenseRecord) {
        expenseRecordList.remove(expenseRecord);
    }
}
