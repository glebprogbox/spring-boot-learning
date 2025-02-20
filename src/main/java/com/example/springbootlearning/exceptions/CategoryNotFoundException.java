package com.example.springbootlearning.exceptions;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends Exception {

    private final String categoryCode;

    public CategoryNotFoundException(String message, String categoryCode) {
        super(message);
        this.categoryCode = categoryCode;
    }
}
