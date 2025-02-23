package com.example.springbootlearning.exceptions;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends Exception {

    private final String code;

    public EntityAlreadyExistsException(String message, String code) {
        super(message);
        this.code = code;
    }
}
