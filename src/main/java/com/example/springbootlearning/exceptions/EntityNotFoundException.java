package com.example.springbootlearning.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends Exception {

    private final String code;

    public EntityNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
}
