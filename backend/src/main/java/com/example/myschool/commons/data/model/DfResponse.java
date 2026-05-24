package com.example.myschool.commons.data.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class DfResponse<T> {
    private Integer code;
    private String message;
    private T result;

    public DfResponse(){};

    public DfResponse(T result) {
        this.code = 0;
        this.result = result;
    }
    public DfResponse(String message, Integer code) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseEntity<DfResponse<T>> okEntity(T body) {
        return ResponseEntity.ok(ok(body));
    }

    public static <T> DfResponse<T> ok(T body) {
        DfResponse<T> response = new DfResponse<>();
        response.setResult(body);
        return response;
    }

    public static DfResponse customRequest(String message, Integer code){
        DfResponse response = new DfResponse(message, code);
        return response;
    }
}
