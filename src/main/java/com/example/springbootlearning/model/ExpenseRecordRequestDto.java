package com.example.springbootlearning.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ExpenseRecordRequestDto {

    @PositiveOrZero(message = "id cannot be negative")
    @Digits(integer = 8, fraction = 0, message = "ID must be an integer and no more than 8 characters")
    private int id;
    @Length(min = 1, max = 100, message = "100 or more characters are used for code")
    @NotBlank(message = "code cannot be blank")
    @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
    private String code;
    @NotNull(message = "amount cannot be null")
    @Digits(integer = 8, fraction = 2, message = "amount must be an double type (0.00) and no more than 10 characters")
    private double amount;
    @NotNull(message = "date cannot be null")
    @PastOrPresent(message = "the date must be in the past or present")
    private LocalDate date;
    @Length(min = 0, max = 1000, message = "1000 or more characters are used for comment")
    private String comment;
}
