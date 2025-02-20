package com.example.springbootlearning.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExpenseRecord {
    @NotNull(message = "employee cannot be null")
    private Employee employee;
    @NotNull(message = "category cannot be null")
    private ExpenseCategory category;
    @NotNull(message = "amount cannot be null")
    @Digits(integer = 8, fraction = 2, message = "amount must be an double type (0.00) and no more than 10 characters")
    private double amount;
    @NotNull(message = "date cannot be null")
    @PastOrPresent(message = "the date must be in the past or present")
    private LocalDate date;
    @Length(min = 1, max = 1000, message = "1000 or more characters are used for comment")
    private String comment;
}
