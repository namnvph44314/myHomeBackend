package com.example.myschool.commons.core.exception;

import com.example.myschool.commons.data.model.DfResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        DfResponse<Map<String,String>> response = new DfResponse<>();
        response.setCode(400);
        response.setMessage("Validation failed");
        response.setResult(errors);

        return ResponseEntity.badRequest().body(response);
    }

}
