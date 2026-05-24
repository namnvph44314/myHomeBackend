package com.example.myschool.config.exception;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationException extends RuntimeException{
    private final Map<String,String> errors;

    public ValidationException(Map<String,String> errors) {
        this.errors = errors;
    }

    public Map<String,String> getErrors() {
        return errors;
    }
}
