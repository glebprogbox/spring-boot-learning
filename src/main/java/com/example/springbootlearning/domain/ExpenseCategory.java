package com.example.springbootlearning.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class ExpenseCategory {
    @Length(min = 1, max = 100, message = "100 or more characters are used for code")
    @NotBlank(message = "code cannot be blank")
    @Pattern(regexp = "[A-ZА-Я_]*", message = "the code must match the template \"A-ZА-Я_\"")
    private final String code;
    @Length(min = 1, max = 1000, message = "1000 or more characters are used for description")
    @NotBlank(message = "description cannot be blank")
    private String description;
}
