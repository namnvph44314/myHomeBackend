package com.example.myschool.config.exception;

import com.example.myschool.commons.data.model.DfResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<DfResponse<Map<String,String>>> handlerValidationException(
            ValidationException ex
    ){
        DfResponse<Map<String,String>> response=new DfResponse<>();
        response.setCode(400);
        response.setMessage("Validation failed");
        response.setResult(ex.getErrors());
        return ResponseEntity.badRequest().body(response);
    }
}
