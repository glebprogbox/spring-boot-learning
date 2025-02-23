package com.example.springbootlearning.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class Employee {
    @PositiveOrZero(message = "id cannot be negative")
    @Digits(fraction = 0, message = "ID must be an long and no more than 18 characters", integer = 18)
    private final long id;
    @Length(min = 1, max = 100, message = "100 or more characters are used for Fullname")
    @NotBlank(message = "fullName cannot be blank")
    private String fullName;
    @Length(min = 1, max = 100, message = "100 or more characters are used for Position")
    @NotBlank(message = "position cannot be blank")
    private String position;
}
