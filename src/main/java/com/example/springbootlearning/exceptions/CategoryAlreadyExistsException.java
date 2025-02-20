package com.example.springbootlearning.exceptions;

import lombok.Getter;

@Getter
public class CategoryAlreadyExistsException extends Exception {

    private final String categoryCode;

    public CategoryAlreadyExistsException(String message, String categoryCode) {
        super(message);
        this.categoryCode = categoryCode;
    }
}
